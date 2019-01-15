package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.AuthToken;
import com.voteva.auth.repository.TokenRepository;
import com.voteva.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    private static final long DEFAULT_TOKEN_TTL_DAYS = 30;

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public AuthToken generateToken() {
        return generateToken(Duration.ofDays(DEFAULT_TOKEN_TTL_DAYS).getSeconds());
    }

    @Override
    public AuthToken generateToken(long tokenTtlSeconds) {
        Instant createTime = Instant.now();
        Instant expireTime = createTime.plusSeconds(tokenTtlSeconds);

        return new AuthToken()
                .setToken(UUID.randomUUID().toString())
                .setValidFromMillis(createTime)
                .setValidTillMillis(expireTime);
    }

    @Override
    public AuthToken getAuthToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthException("Incorrect token"));
    }

    @Override
    public void saveToken(AuthToken token) {
        tokenRepository.save(token);
    }

    @Override
    public void revokeToken(AuthToken token) {
        tokenRepository.delete(token);
    }
}
