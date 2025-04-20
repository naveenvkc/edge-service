package com.polarbookshop.edgeservice.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PathGatewayFilter extends AbstractGatewayFilterFactory<PathGatewayFilter.Config> {

    public PathGatewayFilter() {
        super(Config.class);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Config {
        private String name;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            String path = req.getURI().getRawPath();
            String[] pathParts = path.split("/");

            if (pathParts.length > 3) {
                String apiPath =
                        "/" + String.join("/", Arrays.copyOfRange(pathParts, 3, pathParts.length));
                ServerHttpRequest newRequest = req.mutate().path(apiPath).contextPath(null).build();
                return chain.filter(exchange.mutate().request(newRequest).build());
            }

            ServerHttpRequest newRequest = req.mutate().path("/").contextPath(null).build();
            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }
}
