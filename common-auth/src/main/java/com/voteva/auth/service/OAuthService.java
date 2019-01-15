package com.voteva.auth.service;

import com.voteva.auth.model.entity.OAuthToken;

public interface OAuthService {

    String authorize(String clientId, String userAccessToken);

    OAuthToken getToken(String clientId, String clientSecret, String authorizationCode);

    OAuthToken refreshToken(String clientId, String clientSecret, String refreshToken);
}
