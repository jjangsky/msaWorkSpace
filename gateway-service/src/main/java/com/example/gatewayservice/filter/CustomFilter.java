package com.example.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // pre Filter
        return (exchange, chain) ->{
            // 서버 요청으로 부터 정보를 일단 가져와야함
          ServerHttpRequest request = exchange.getRequest();
          ServerHttpResponse response = exchange.getResponse();

          log.info("Custom Pre Filter Id값 확인 -> {}", request.getId());

          // post Filter
          return chain.filter(exchange).then(Mono.fromRunnable(()->{
              // Mono 타입은 Spring webFlux 에서 추가된 방식으로 동기 방식이 아닌
              // 비동기 방식의 서버를 지원할 때 데이터의 단일값 전달할 때 사용
              log.info("Custom Post Filter 확인 -> {}", response.getStatusCode());
          }));

        };
    }

    public static class Config {

    }
}
