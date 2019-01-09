package com.voteva.gateway.service.impl;

import com.voteva.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.voteva.auth.grpc.model.v1.GGenerateTokenRequest;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyRequest;
import com.voteva.auth.grpc.model.v1.GPrincipalKey;
import com.voteva.auth.grpc.model.v1.GToken;
import com.voteva.gateway.annotation.GatewayService;
import com.voteva.gateway.converter.AuthConverter;
import com.voteva.gateway.grpc.client.GRpcAuthServiceClient;
import com.voteva.gateway.grpc.client.GRpcCredentialsServiceClient;
import com.voteva.gateway.grpc.client.GRpcUsersServiceClient;
import com.voteva.gateway.security.config.TokenConfig;
import com.voteva.gateway.security.model.Authentication;
import com.voteva.gateway.service.AuthenticationService;
import com.voteva.gateway.web.to.in.LoginUserRequest;
import com.voteva.gateway.web.to.out.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.voteva.gateway.exception.model.Service.COMMON_AUTH;

@GatewayService(serviceName = COMMON_AUTH)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenConfig tokenConfig;
    private final GRpcAuthServiceClient rpcAuthServiceClient;
    private final GRpcCredentialsServiceClient rpcCredentialsServiceClient;
    private final GRpcUsersServiceClient rpcUsersServiceClient;

    @Autowired
    public AuthenticationServiceImpl(
            TokenConfig tokenConfig,
            GRpcAuthServiceClient rpcAuthServiceClient,
            GRpcCredentialsServiceClient rpcCredentialsServiceClient,
            GRpcUsersServiceClient rpcUsersServiceClient) {

        this.tokenConfig = tokenConfig;
        this.rpcAuthServiceClient = rpcAuthServiceClient;
        this.rpcCredentialsServiceClient = rpcCredentialsServiceClient;
        this.rpcUsersServiceClient = rpcUsersServiceClient;
    }

    @Override
    public LoginInfo login(
            LoginUserRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        GPrincipalKey principalKey = getPrincipalKey(request);
        GToken token = generateToken();

        Authentication authentication = AuthConverter.toAuthentication(
                rpcAuthServiceClient.authenticateAny(
                        GAuthenticateAnyRequest.newBuilder()
                                .setToken(token)
                                .setPrincipalKey(principalKey)
                                .setFingerPrint(AuthConverter.toGFingerPrint(httpServletRequest))
                                .build())
                        .getAuthentication());

        return new LoginInfo(authentication.getToken());
    }

    @Override
    public Authentication getAuthentication(String token) {
        return AuthConverter.toAuthentication(
                rpcAuthServiceClient.getAuthentication(
                        GGetAuthenticationRequest.newBuilder()
                                .setToken(token)
                                .build())
                        .getAuthentication());
    }

    private GPrincipalKey getPrincipalKey(LoginUserRequest request) {
        return rpcCredentialsServiceClient.getPrincipalKey(
                GGetPrincipalKeyRequest.newBuilder()
                        .setCredentials(AuthConverter.toGCredentials(request))
                        .build())
                .getPrincipalKey();
    }

    private GToken generateToken() {
        return rpcAuthServiceClient.generateToken(
                GGenerateTokenRequest.newBuilder()
                        .setTokenTtlSeconds(tokenConfig.getTokenTtlSeconds())
                        .build())
                .getToken();
    }
}
