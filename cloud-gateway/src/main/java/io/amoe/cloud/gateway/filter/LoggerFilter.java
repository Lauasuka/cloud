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
 */
public class LoggerFilter implements GlobalFilter, Ordered {

    Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    private static final String UA = "user-agent";

    @Override
    public int getOrder() {
        return FilterOrderConstant.LOGGER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).doFinally(signalType -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("========================================== Start ==========================================");
            logger.info("Request.URI       : {}", request.getURI().toString());
            logger.info("Request.Path      : {}", request.getPath());
            logger.info("Request.Method    : {}", request.getMethod());
            logger.info("Request.Params    : {}", request.getQueryParams());
            logger.info("Request.IP        : {}", request.getRemoteAddress());
            logger.info("Request.UA        : {}", request.getHeaders().get(UA));
            logger.info("Request.Cookies   : {}", request.getCookies());
            logger.info("========================================== End ==========================================");
        });
    }
}
