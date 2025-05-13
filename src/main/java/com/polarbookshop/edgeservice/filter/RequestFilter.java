//package com.polarbookshop.edgeservice.filter;
//
//import org.jetbrains.annotations.NotNull;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.Objects;
//
//@Component
//public class RequestFilter implements WebFilter {
//
//    private static final String ATTR_CHECK = "_polar_once_per_request_";
//
//    @Override
//    public @NotNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        if (Objects.nonNull(exchange.getAttributes().get(ATTR_CHECK))) {
//            return chain.filter(exchange);
//        }
//        exchange.getAttributes().put(ATTR_CHECK, true);
//        final ServerHttpRequest request = exchange.getRequest();
//        System.out.println(" path::" + request.getPath());
//        return chain.filter(exchange);
//    }
//}
