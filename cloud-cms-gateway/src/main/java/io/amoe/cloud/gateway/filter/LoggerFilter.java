package io.amoe.cloud.gateway.filter;

import io.amoe.cloud.gateway.constant.FilterOrderConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Amoe
 * @date 2020/4/23 15:22
 */
public class LoggerFilter implements GlobalFilter, Ordered {

    Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public int getOrder() {
        return FilterOrderConstant.LOGGER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("========================================== Start ==========================================");
        logger.info("访问地址    : {}", request.getPath());
        logger.info("请求方式    : {}", request.getMethod());
        logger.info("请求参数    : {}", request.getQueryParams());
        logger.info("实体参数    : {}", request.getBody().toString());
        logger.info("访问者IP    : {}", request.getRemoteAddress());
        logger.info("cookie内容 : {}", request.getCookies());
        logger.info("========================================== End ==========================================");
        return chain.filter(exchange);
    }
}
