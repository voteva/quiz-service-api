package com.voteva.auth.service;

import com.voteva.auth.model.entity.Token;

public interface TokenService {

    Token generateToken();
    Token generateToken(long tokenTtlSeconds);
}
