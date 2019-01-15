package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.AuthToken;
import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.model.entity.FingerPrint;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.service.AuthenticationService;
import com.voteva.auth.service.PrincipalService;
import com.voteva.auth.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final long TOKEN_INACTION_MAX_SECONDS = 1800L;

    private final PrincipalService principalService;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationServiceImpl(
            PrincipalService principalService,
            TokenService tokenService) {
        this.principalService = principalService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticateAny(
            AuthToken token,
            PrincipalKey principalKey,
            FingerPrint fingerPrint) {

        PrincipalKey principal = principalService.getPrincipalByExternalId(principalKey.getExtId());
        if (token == null) {
            token = tokenService.generateToken();
        }

        token.setPrincipalId(principal.getExtId());
        token.setLastAccessMillis(Instant.now());

        tokenService.saveToken(token);

        return new Authentication(token, principal);
    }

    @Override
    public Authentication getAuthentication(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new AuthException("Incorrect token");
        }

        AuthToken authToken = tokenService.getAuthToken(token);
        validateToken(authToken);

        authToken.setLastAccessMillis(Instant.now());
        tokenService.saveToken(authToken);

        PrincipalKey principal = principalService.getPrincipalByExternalId(authToken.getPrincipalId());

        return new Authentication(authToken, principal);
    }

    @Override
    public void revokeAuthentication(String token) {
        AuthToken authToken = tokenService.getAuthToken(token);
        tokenService.revokeToken(authToken);
    }

    private void validateToken(AuthToken token) {
        Instant now = Instant.now();

        if (token.getValidFromMillis().compareTo(now) > 0) {
            throw new AuthException("AuthToken is not accessible yet");
        }

        if (token.getValidTillMillis().compareTo(now) < 0) {
            tokenService.revokeToken(token);
            throw new AuthException("AuthToken expired");
        }

        if (now.getEpochSecond() - token.getLastAccessMillis().getEpochSecond() > TOKEN_INACTION_MAX_SECONDS) {
            tokenService.revokeToken(token);
            throw new AuthException("Session expired");
        }
    }
}
