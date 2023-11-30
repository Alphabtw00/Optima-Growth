/* dont need it as sleuth(micrometer) covers it
package com.optimagrowth.license.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserContext { //object to store correlation id and other useful info from incoming request
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private String correlationId= new String();
    private String authToken= new String();
    private String userId = new String();
    private String organizationId = new String();
}
 */
