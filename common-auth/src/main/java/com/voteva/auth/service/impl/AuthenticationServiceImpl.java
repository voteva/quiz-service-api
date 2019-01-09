package com.voteva.auth.service.impl;

import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.model.entity.FingerPrint;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.model.entity.Token;
import com.voteva.auth.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public Authentication authenticateAny(
            Token token,
            PrincipalKey principalKey,
            FingerPrint fingerPrint) {

        // check principal exists

        return new Authentication(
                new Token(
                        "1232423523412",
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now())),
                new PrincipalKey("ss", UUID.randomUUID().toString()));
    }

    @Override
    public Authentication getAuthentication(String token) {
        return new Authentication(
                new Token(
                        "1232423523412",
                        Timestamp.from(Instant.now()),
                        Timestamp.from(Instant.now())),
                new PrincipalKey("ss", UUID.randomUUID().toString()));
    }
}
