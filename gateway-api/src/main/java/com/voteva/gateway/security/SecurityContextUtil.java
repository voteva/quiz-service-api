package com.voteva.gateway.security;

import com.voteva.gateway.exception.GatewayApiException;
import com.voteva.gateway.exception.model.Service;
import com.voteva.gateway.security.model.AuthenticationToken;
import com.voteva.gateway.security.model.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityContextUtil {

    public static AuthenticationToken getAuthentication() {
        return (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public static Principal getPrincipal() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> (Principal) auth.getPrincipal())
                .orElseThrow(() -> new GatewayApiException(
                        Service.GATEWAY,
                        HttpStatus.UNAUTHORIZED,
                        "No user logged in"));
    }
}
