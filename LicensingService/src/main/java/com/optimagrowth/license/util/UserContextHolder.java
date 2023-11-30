/* dont need it as sleuth(micrometer) covers it
package com.optimagrowth.license.util;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Data
@Component
public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>(); //adds the userContext
    // object to a thread

    public static final UserContext getContext(){
        UserContext context = userContext.get();
        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }
        return userContext.get();
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }
    public static final UserContext createEmptyContext(){
        return new UserContext();
    }
}

 */
