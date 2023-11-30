package com.optimagrowth.license.service.feignClient;

//import com.optimagrowth.license.util.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Data
public class FeignInterceptor implements RequestInterceptor {
    public static String getBearerTokenHeader() { //use it if getbearerToken() doesnt work
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
    public AbstractOAuth2Token getBearerToken(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if((authentication.getCredentials() instanceof AbstractOAuth2Token)&&authentication!=null){
            return (AbstractOAuth2Token) authentication.getCredentials();
        }
        return null;
    }


    @Override
    public void apply(RequestTemplate template) {
        //template.header("tmx-correlation-id", UserContextHolder.getContext().getCorrelationId());
        //template.header("tmx-user-id", UserContextHolder.getContext().getUserId());
        //template.header("tmx-organization-id", UserContextHolder.getContext().getOrganizationId());
        AbstractOAuth2Token bearerToken = getBearerToken();
        if (bearerToken != null) {
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken.getTokenValue());
        }
    }
}
