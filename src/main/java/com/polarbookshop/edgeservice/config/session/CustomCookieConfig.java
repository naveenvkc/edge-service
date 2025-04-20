package com.polarbookshop.edgeservice.config.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

@Configuration
@EnableRedisWebSession(maxInactiveIntervalInSeconds = 600)
public class CustomCookieConfig {

    @Bean
    public WebSessionIdResolver headerWebSessionIdResolver() {
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver(); //
        resolver.addCookieInitializer((builder) -> builder.path("/"));
        return resolver;
    }
}
