package com.voteva.gateway.service;

import com.voteva.gateway.security.model.AuthenticationToken;
import com.voteva.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.voteva.gateway.web.to.in.OAuthTokenRequest;
import com.voteva.gateway.web.to.out.OAuthCodeResponse;
import com.voteva.gateway.web.to.out.OAuthTokenResponse;

public interface OAuth2Service {

    OAuthCodeResponse authorize(String clientId, AuthenticationToken authentication);

    OAuthTokenResponse getToken(OAuthTokenRequest request);

    OAuthTokenResponse refreshToken(OAuthRefreshTokenRequest request);
}
