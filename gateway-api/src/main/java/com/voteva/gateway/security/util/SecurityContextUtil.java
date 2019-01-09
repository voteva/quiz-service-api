package com.voteva.gateway.security.util;

import com.voteva.gateway.security.model.Principal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static Principal getPrincipal() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
