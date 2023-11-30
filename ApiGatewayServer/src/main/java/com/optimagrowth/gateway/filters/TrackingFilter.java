/* to set trace id in headers of all request if not present (micrometer does it so we dont need it)  */
package com.optimagrowth.gateway.filters;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@Slf4j
public class TrackingFilter implements GlobalFilter {
    private FilterUtil filterUtil;
    private Tracer tracer;

    public TrackingFilter(FilterUtil filterUtil) {
        this.filterUtil = filterUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
      /*if(hasCorrelationId(httpHeaders)){
            log.info("tmx-correlation-id found in tracking filter: {}. ",
                    filterUtil.getCorrelationId(httpHeaders));
        }
        else {
            String correlationId = UUID.randomUUID().toString();
            var exchange1 = filterUtil.setCorrelationIdInRequest(exchange,correlationId);
            log.info("tmx-correlation-id generated in tracking filter: {}. ",
                     correlationId);
            log.info("The authentication name from the token is : " + getUsername(httpHeaders));
            return chain.filter(exchange1);
        } */
        log.info("tmx-correlation-id found in tracking filter: {}. ",
                tracer.currentSpan().context().traceId());
        log.info("The authentication name from the token is : " + getUsername(httpHeaders));
        return chain.filter(exchange);
    }
    private String getUsername(HttpHeaders requestHeaders){
        String token = filterUtil.getAuthToken(requestHeaders);
        if(token!=null && token.startsWith("Bearer ")){
            String jwtToken = token.substring(7);
            try{
                JWT jwt = JWTParser.parse(jwtToken);
                if (jwt instanceof SignedJWT) {
                    SignedJWT signedJWT = (SignedJWT) jwt;
                    JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                    String preferredUsername = claimsSet.getStringClaim("preferred_username");
                    return preferredUsername;
                }
            }catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

  /*public boolean hasCorrelationId(HttpHeaders httpHeaders){
        return filterUtil.getCorrelationId(httpHeaders)!=null;
    }*/


}


