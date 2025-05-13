package com.polarbookshop.edgeservice.config.security;

import com.polarbookshop.edgeservice.config.handler.CustomLogoutSuccessHandler;
import org.jetbrains.annotations.Debug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestHandler;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String GROUPS_CLAIM = "groups";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";


    @Bean
    SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity security,
            ReactiveClientRegistrationRepository clientRegistrationRepository) {

        ServerCsrfTokenRequestHandler csrfRequestHandler = new ServerCsrfTokenRequestAttributeHandler();
        //csrfRequestHandler.setCsrfRequestAttributeName("_csrf");
        return security
                //.addFilterBefore(requestFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                //.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/", "/*.css", "/*.js", "/favicon.ico").permitAll()
                        .pathMatchers(HttpMethod.GET, "/app/catalog-api/api/v1/books/**").permitAll()
                        .anyExchange().authenticated())
                //.formLogin(Customizer.withDefaults())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(
                                new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessHandler(
                        oidcLogoutSuccessHandler(clientRegistrationRepository)))
                //.logout(logout -> logout.logoutSuccessHandler(
                //        new CustomLogoutSuccessHandler()))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler()))
                .build();
    }


    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(
            ReactiveClientRegistrationRepository clientRegistrationRepository) {
        var oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        return oidcLogoutSuccessHandler;
    }

    @Bean
    WebFilter csrfWebFilter() {
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> Mono.defer(() -> {
                Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
                return csrfToken != null ? csrfToken.then() : Mono.empty();
            }));
            return chain.filter(exchange);
        };
    }

    @Bean
    ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new WebSessionServerOAuth2AuthorizedClientRepository();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/app/catalog-api/api/v1/books/**");
    }

//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            authorities.forEach(
//                    authority -> {
//                        if (authority instanceof OidcUserAuthority) {
//                            OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//                            OidcIdToken oidcIdToken = oidcUserAuthority.getIdToken();
//                            LOG.info("Username from id token: {}", oidcIdToken.getPreferredUsername());
//                            OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//                            Map<String, Object> caliamsMap = userInfo.getClaims();
//                            LOG.info("caliamsMap: {}", caliamsMap);
//                            if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
//                                var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
//                                var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
//                                mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
//                            }
//
//                            List<SimpleGrantedAuthority> groupAuthorities =
//                                    userInfo.getClaimAsStringList(GROUPS_CLAIM).stream()
//                                            .map(group ->
//                                                    new SimpleGrantedAuthority(ROLE_PREFIX + group.toUpperCase()))
//                                            .collect(Collectors.toList());
//                            groupAuthorities.forEach(auth -> {
//                                LOG.info("role: {}", auth.getAuthority());
//                            });
//                            mappedAuthorities.addAll(groupAuthorities);
//
//                            mappedAuthorities.forEach(auth -> {
//                                LOG.info("role: {}", auth.getAuthority());
//                            });
//                        }
//                    });
//            return mappedAuthorities;
//        };
//    }
//
//    Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
//    }

}
