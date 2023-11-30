package com.optimagrowth.organization.security;



import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt,AbstractAuthenticationToken> { //AbstractAuthToken implements authentication interface and type of token spring uses to represent a logged in user

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();  //default jwt converter by spring
    private final JwtAuthConverterProperties properties;

    public JwtAuthConverter(JwtAuthConverterProperties properties) {
        this.properties = properties;
    }

    
    @Override
    public AbstractAuthenticationToken convert(@NonNull  Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(            //concat is joining 2 stream objects
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        )
                .collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));  //returning new authentication token with our roles and authorities
    }


    private String getPrincipalClaimName(Jwt jwt) {   //getting principal name
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        return jwt.getClaim(claimName);  //if there is a username sent(sent as default with key as preffered_username and value as actual username) we return that otherwise we use jwtclainnames.SUB which returns a coded string
    }


    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) { //jwt token has these keyvalue pairs list (resourceAccess has resource(different clients) and resource has resource Roles(user roles), so we check all and return empty set if any of them is empty)
        Map<String, Object> resourceAccess;   //resource_access that contains all clients
        Map<String, Object> resource; //clients that contains all roles
        Collection<String> resourceRoles;//roles


        if(jwt.getClaim("resource_access")==null){
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");

        if(resourceAccess.get(properties.getResourceId())==null){
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId());



        resourceRoles = (Collection<String>) resource.get("roles"); //collecting all roles from our current client(this service)

        return resourceRoles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet()); //returning as set cause simplegrantedauthority requires set (only one role of one name)
    }
}
