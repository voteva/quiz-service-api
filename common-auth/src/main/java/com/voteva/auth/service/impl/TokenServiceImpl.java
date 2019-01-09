package com.voteva.auth.service.impl;

import com.voteva.auth.model.entity.Token;
import com.voteva.auth.service.TokenService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private static final long DEFAULT_TOKEN_TTL_DAYS = 365;

    @Override
    public Token generateToken() {
        return generateToken(Duration.ofDays(DEFAULT_TOKEN_TTL_DAYS).getSeconds());
    }

    @Override
    public Token generateToken(long tokenTtlSeconds) {
        Instant createTime = Instant.now();
        Instant expireTime = createTime.plusSeconds(tokenTtlSeconds);

        return new Token(
                UUID.randomUUID().toString(),
                Timestamp.from(createTime),
                Timestamp.from(expireTime));
    }
}
