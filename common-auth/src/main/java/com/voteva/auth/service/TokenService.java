package com.voteva.auth.service;

import com.voteva.auth.model.entity.AuthToken;

public interface TokenService {

    AuthToken generateToken();
    AuthToken generateToken(long tokenTtlSeconds);
    AuthToken getAuthToken(String token);
    void saveToken(AuthToken token);
    void revokeToken(AuthToken token);
}
