package com.voteva.auth.converter;

import com.voteva.auth.grpc.model.v1.GAuthentication;
import com.voteva.auth.grpc.model.v1.GCredentials;
import com.voteva.auth.grpc.model.v1.GFingerPrint;
import com.voteva.auth.grpc.model.v1.GPrincipalKey;
import com.voteva.auth.grpc.model.v1.GToken;
import com.voteva.auth.model.entity.AuthToken;
import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.model.entity.Credentials;
import com.voteva.auth.model.entity.FingerPrint;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.common.grpc.model.GUuid;

import java.time.Instant;
import java.util.UUID;

public class ModelConverter {

    public static GAuthentication convert(Authentication authentication) {
        return GAuthentication.newBuilder()
                .setToken(convert(authentication.getToken()))
                .setPrincipal(convert(authentication.getPrincipalKey()))
                .build();
    }

    public static String toToken(GAuthentication authentication) {
        return authentication.getToken().getToken();
    }

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }

    public static GToken convert(AuthToken token) {
        return GToken.newBuilder()
                .setToken(token.getToken())
                .setValidFromMills(token.getValidFromMillis().toEpochMilli())
                .setValidTillMills(token.getValidTillMillis().toEpochMilli())
                .build();
    }

    public static AuthToken convert(GToken token) {
        return new AuthToken()
                .setToken(token.getToken())
                .setValidFromMillis(Instant.ofEpochMilli(token.getValidFromMills()))
                .setValidTillMillis(Instant.ofEpochMilli(token.getValidTillMills()));
    }

    public static Credentials convert(GCredentials credentials) {
        return new Credentials();
    }

    public static GPrincipalKey convert(PrincipalKey principalKey) {
        return GPrincipalKey.newBuilder()
                .setExtId(principalKey.getExtId())
                .build();
    }

    public static PrincipalKey convert(GPrincipalKey principalKey) {
        return new PrincipalKey()
                .setExtId(principalKey.getExtId());
    }

    public static FingerPrint convert(GFingerPrint fingerPrint) {
        return new FingerPrint();
    }
}
