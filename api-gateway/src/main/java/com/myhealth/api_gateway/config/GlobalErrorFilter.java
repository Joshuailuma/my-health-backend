package com.myhealth.api_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class GlobalErrorFilter extends AbstractGatewayFilterFactory<GlobalErrorFilter.Config> {

    public GlobalErrorFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange)
                .onErrorResume(throwable -> {
                    // Customize the error response here
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    response.getHeaders().add("Content-Type", "application/json");

                    DataBuffer dataBuffer = response.bufferFactory()
                            .wrap(("{\"error\": \"" + throwable.getMessage() + "\"}").getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(dataBuffer));
                });
    }

    public static class Config {
        // Config fields if needed
    }
}
