package com.voteva.gateway.service.impl;

import com.voteva.auth.grpc.model.v1.GOAuthAuthorizeRequest;
import com.voteva.gateway.annotation.GatewayService;
import com.voteva.gateway.converter.AuthConverter;
import com.voteva.gateway.grpc.client.GRpcOAuthServiceClient;
import com.voteva.gateway.security.model.AuthenticationToken;
import com.voteva.gateway.service.OAuth2Service;
import com.voteva.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.voteva.gateway.web.to.in.OAuthTokenRequest;
import com.voteva.gateway.web.to.out.OAuthCodeResponse;
import com.voteva.gateway.web.to.out.OAuthTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voteva.gateway.exception.model.Service.COMMON_AUTH;

@GatewayService(serviceName = COMMON_AUTH)
public class OAuth2ServiceImpl implements OAuth2Service {

    private final GRpcOAuthServiceClient rpcOAuthServiceClient;

    @Autowired
    public OAuth2ServiceImpl(GRpcOAuthServiceClient rpcOAuthServiceClient) {
        this.rpcOAuthServiceClient = rpcOAuthServiceClient;
    }

    @Override
    public OAuthCodeResponse authorize(String clientId, AuthenticationToken authentication) {
        return new OAuthCodeResponse(
                rpcOAuthServiceClient.authorize(
                        GOAuthAuthorizeRequest.newBuilder()
                                .setClientId(clientId)
                                .setUserAccessToken(authentication.getToken())
                                .build())
                        .getAuthorizationCode());
    }

    @Override
    public OAuthTokenResponse getToken(OAuthTokenRequest request) {
        return AuthConverter.convert(rpcOAuthServiceClient.getToken(AuthConverter.convert(request)));
    }

    @Override
    public OAuthTokenResponse refreshToken(OAuthRefreshTokenRequest request) {
        return AuthConverter.convert(rpcOAuthServiceClient.refreshToken(AuthConverter.convert(request)));
    }
}
