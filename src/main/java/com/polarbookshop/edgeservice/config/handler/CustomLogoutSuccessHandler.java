package com.polarbookshop.edgeservice.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CustomLogoutSuccessHandler implements ServerLogoutSuccessHandler {

    public static final String DEFAULT_LOGOUT_SUCCESS_URL = "{baseUrl}";
    private final URI logoutSuccessUrl = URI.create(DEFAULT_LOGOUT_SUCCESS_URL);
    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        exchange.getExchange().getSession().flatMap(WebSession::invalidate);
        return this.redirectStrategy.sendRedirect(exchange.getExchange(), this.logoutSuccessUrl);
    }
}
