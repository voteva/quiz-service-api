package com.voteva.gateway.security;

import com.voteva.auth.grpc.model.v1.GAuthenticateServiceRequest;
import com.voteva.auth.grpc.model.v1.GAuthentication;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.voteva.gateway.converter.AuthConverter;
import com.voteva.gateway.grpc.client.GRpcAuthServiceClient;
import com.voteva.gateway.security.model.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalAuthService {

    private static final Logger logger = LoggerFactory.getLogger(InternalAuthService.class);

    private final GRpcAuthServiceClient rpcAuthServiceClient;
    private final GatewayCredentialsHolder credentialsHolder;

    private Authentication internalAuthentication;

    @Autowired
    public InternalAuthService(
            GRpcAuthServiceClient rpcAuthServiceClient,
            GatewayCredentialsHolder credentialsHolder) {
        this.rpcAuthServiceClient = rpcAuthServiceClient;
        this.credentialsHolder = credentialsHolder;
    }

    public GAuthentication getGAuthentication() {
        return AuthConverter.toGAuthentication(getInternalAuthentication());
    }

    public Authentication getInternalAuthentication() {
        if (internalAuthentication == null || isAuthenticationExpired()) {
            internalAuthentication = AuthConverter.toAuthentication(
                    rpcAuthServiceClient.authenticateService(
                            GAuthenticateServiceRequest.newBuilder()
                                    .setServiceId(credentialsHolder.getServiceId())
                                    .setServiceSecret(credentialsHolder.getServiceSecret())
                                    .build())
                            .getAuthentication());
        }

        return internalAuthentication;
    }

    private boolean isAuthenticationExpired() {
        try {
            rpcAuthServiceClient.getAuthentication(
                    GGetAuthenticationRequest.newBuilder()
                            .setToken(internalAuthentication.getToken())
                            .build());

            return false;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return true;
        }
    }
}
