package com.example.gatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GolbalFilter extends AbstractGatewayFilterFactory<GolbalFilter.Config> {
    public GolbalFilter(){
        super(Config.class);
    }
    // Global Filter -> 라우터에 필터 따로 등록 안해도 전역으로 작동
    // pre는 가장 처음에 작동하고 post는 가장 마지막에 작동

    @Override
    public GatewayFilter apply(Config config) {
        // pre Filter
        return (exchange, chain) ->{
          ServerHttpRequest request = exchange.getRequest();
          ServerHttpResponse response = exchange.getResponse();

          log.info("Global Filter baseMessage -> {}", config.getBaseMessage());
          if(config.isPreLogger()){
              log.info("Global Filter Start : request id -> {}", request.getId());
          }
          // post Filter
          return chain.filter(exchange).then(Mono.fromRunnable(()->{
              if(config.isPostLogger()){
                  log.info("Global Filter End : Response id -> {}", response.getStatusCode());
              }

          }));

        };
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

    }
}
