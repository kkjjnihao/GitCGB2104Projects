package com.cy.jt;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    /**
     * 对请求进行过滤
     * @param exchange (网关层面的交换器,动过此对象获得请求,响应对象)
     * @param chain 过滤链(这个过滤链中包含0或多个过滤器)
     * @return Mono代表Spring WebFlux技术中的0个或一个响应序列
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String username = request.getQueryParams().getFirst("username");
//        if(!"admin".equals(username)) {
//            System.out.println("认证失败");
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
        //请求链继续传递(假如用户名为admin则继续执行后续操作)
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
