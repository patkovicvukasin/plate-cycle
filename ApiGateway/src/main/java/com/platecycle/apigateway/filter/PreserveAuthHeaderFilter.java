package com.platecycle.apigateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PreserveAuthHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Možeš ovde proveriti i eventualno logovati Authorization header,
        // ali ovde samo prosleđujemo zahtev dalje ne menjajući zaglavlja.
        ServerHttpRequest request = exchange.getRequest();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // Ovo određuje redosled izvršavanja filtera
        return -1; // Visoki prioritet (niži broj znači viši prioritet)
    }
}
