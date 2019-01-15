package com.voteva.gateway.converter;

import com.voteva.auth.grpc.model.v1.GAuthentication;
import com.voteva.auth.grpc.model.v1.GCredentials;
import com.voteva.auth.grpc.model.v1.GFingerPrint;
import com.voteva.auth.grpc.model.v1.GLogin;
import com.voteva.auth.grpc.model.v1.GOAuthRefreshTokenRequest;
import com.voteva.auth.grpc.model.v1.GOAuthRefreshTokenResponse;
import com.voteva.auth.grpc.model.v1.GOAuthTokenRequest;
import com.voteva.auth.grpc.model.v1.GOAuthTokenResponse;
import com.voteva.auth.grpc.model.v1.GPrincipalKey;
import com.voteva.auth.grpc.model.v1.GSecret;
import com.voteva.gateway.security.model.Authentication;
import com.voteva.gateway.security.model.Principal;
import com.voteva.gateway.web.to.in.LoginUserRequest;
import com.voteva.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.voteva.gateway.web.to.in.OAuthTokenRequest;
import com.voteva.gateway.web.to.out.OAuthTokenResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.UUID;

public class AuthConverter {

    public static Authentication toAuthentication(GAuthentication authentication) {
        return new Authentication(
                authentication.getToken().getToken(),
                toPrincipal(authentication.getPrincipal()));
    }

    public static Principal toPrincipal(GPrincipalKey principal) {
        return new Principal(
                principal.getSubsystem(),
                UUID.fromString(principal.getExtId()));
    }

    public static GFingerPrint toGFingerPrint(HttpServletRequest request) {
        return GFingerPrint.newBuilder()
                .build();
    }

    public static GCredentials toGCredentials(LoginUserRequest request) {
        return GCredentials.newBuilder()
                .setLogin(toGLogin(request.getUsername()))
                .setSecret(toGSecret(request.getPassword()))
                .build();
    }

    public static GOAuthTokenRequest convert(OAuthTokenRequest request) {
        return GOAuthTokenRequest.newBuilder()
                .setClientId(request.getClientId())
                .setClientSecret(request.getClientSecret())
                .setAuthorizationCode(request.getAuthorizationCode())
                .build();
    }

    public static GOAuthRefreshTokenRequest convert(OAuthRefreshTokenRequest request) {
        return GOAuthRefreshTokenRequest.newBuilder()
                .setClientId(request.getClientId())
                .setClientSecret(request.getClientSecret())
                .setRefreshToken(request.getRefreshToken())
                .build();
    }

    public static OAuthTokenResponse convert(GOAuthTokenResponse response) {
        return new OAuthTokenResponse(
                response.getAccessToken(),
                response.getTokenType(),
                Instant.ofEpochMilli(response.getExpiresIn()),
                response.getRefreshToken());
    }

    public static OAuthTokenResponse convert(GOAuthRefreshTokenResponse response) {
        return new OAuthTokenResponse(
                response.getAccessToken(),
                response.getTokenType(),
                Instant.ofEpochMilli(response.getExpiresIn()),
                response.getRefreshToken());
    }

    private static GLogin toGLogin(String login) {
        return GLogin.newBuilder()
                .setType(GLogin.GLoginType.EMAIL)
                .setValue(login)
                .build();
    }

    private static GSecret toGSecret(String secret) {
        return GSecret.newBuilder()
                .setType(GSecret.GSecretType.PASSWORD)
                .setValue(secret)
                .build();
    }
}
