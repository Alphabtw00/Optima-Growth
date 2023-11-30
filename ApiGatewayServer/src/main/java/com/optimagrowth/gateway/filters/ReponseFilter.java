package com.optimagrowth.gateway.filters;


import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReponseFilter implements GlobalFilter {
    private FilterUtil filterUtil;
    private Tracer tracer;

    public ReponseFilter(FilterUtil filterUtil) {
        this.filterUtil = filterUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            String traceId = tracer.currentSpan().context().traceId();
            log.info("Adding the correlation id to the outbound headers. {}", traceId);
            exchange.getResponse().getHeaders().add(filterUtil.CORRELATION_ID,traceId);
            log.info("Completing outgoing request for {}.", exchange.getRequest().getURI());
        }));
    }
}
