package com.voteva.gateway.security.util;

import com.voteva.gateway.exception.GatewayApiException;
import com.voteva.gateway.exception.model.Service;
import com.voteva.gateway.security.model.Authentication;
import com.voteva.gateway.security.model.AuthenticationData;
import com.voteva.gateway.security.model.Session;
import com.voteva.gateway.security.model.User;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class SecurityContextUtil {

    private static final ThreadLocal<AuthenticationData> CONTEXT = new ThreadLocal<>();

    public static void setAuthenticationData(AuthenticationData authenticationData) {
        CONTEXT.set(authenticationData);
    }

    public static Optional<AuthenticationData> getAuthenticationData() {
        return Optional.ofNullable(CONTEXT.get());
    }

    public static void clearAuthenticationData() {
        CONTEXT.remove();
    }

    public static User getUser() {
        return new User();
        /*return getSession()
                .map(Session::getUser)
                .orElseThrow(() -> new GatewayApiException(Service.GATEWAY, HttpStatus.UNAUTHORIZED, "No user logged in"));*/
    }

    public static Optional<Session> getSession() {
        return getAuthenticationData().map(AuthenticationData::getSession);
    }

    public static Optional<Authentication> getAuthentication() {
        return getAuthenticationData().map(AuthenticationData::getAuthentication);
    }

    private SecurityContextUtil() {
        throw new UnsupportedOperationException();
    }
}
