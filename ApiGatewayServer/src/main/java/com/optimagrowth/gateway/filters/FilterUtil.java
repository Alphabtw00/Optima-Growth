package com.optimagrowth.gateway.filters;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@Data
public class FilterUtil {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "Authorization";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORG_ID = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";



    public String getAuthToken(HttpHeaders requestHeaders) {
        String token = requestHeaders.getFirst(AUTH_TOKEN);
        return token;
    }

  /*public String getCorrelationId(HttpHeaders requestHeaders){
        String header = requestHeaders.getFirst(CORRELATION_ID);
        return header;
    }

    public ServerWebExchange setCorrelationIdInRequest(ServerWebExchange exchange, String correlationId){
        //need to mutate exchange and request as they are immutable and need to make a copy to change
        ServerHttpRequest request = exchange.getRequest().mutate().header(CORRELATION_ID,correlationId).build();
        return exchange.mutate().request(request).build();
    }

     public ServerWebExchange setCorrelationIdInReponse(ServerWebExchange exchange, String correlationID){
         exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationID); //no need to mutate as reponses are mutable
         return exchange;
     }*/

}
