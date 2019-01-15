package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.AuthToken;
import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.model.entity.OAuthCode;
import com.voteva.auth.model.entity.OAuthRefreshToken;
import com.voteva.auth.model.entity.OAuthToken;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.repository.OAuthCodeRepository;
import com.voteva.auth.repository.OAuthRefreshTokenRepository;
import com.voteva.auth.service.AuthenticationService;
import com.voteva.auth.service.CredentialsService;
import com.voteva.auth.service.OAuthService;
import com.voteva.auth.service.PrincipalService;
import com.voteva.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class OAuthServiceImpl implements OAuthService {

    private static final long DEFAULT_CODE_TTL_DAYS = 1;
    private static final long DEFAULT_REFRESH_TOKEN_TTL_DAYS = 365;

    private final OAuthCodeRepository oAuthCodeRepository;
    private final OAuthRefreshTokenRepository oAuthRefreshTokenRepository;
    private final TokenService tokenService;
    private final CredentialsService credentialsService;
    private final AuthenticationService authenticationService;
    private final PrincipalService principalService;

    @Autowired
    public OAuthServiceImpl(
            OAuthCodeRepository oAuthCodeRepository,
            OAuthRefreshTokenRepository oAuthRefreshTokenRepository,
            TokenService tokenService,
            CredentialsService credentialsService,
            AuthenticationService authenticationService,
            PrincipalService principalService) {

        this.oAuthCodeRepository = oAuthCodeRepository;
        this.oAuthRefreshTokenRepository = oAuthRefreshTokenRepository;
        this.tokenService = tokenService;
        this.credentialsService = credentialsService;
        this.authenticationService = authenticationService;
        this.principalService = principalService;
    }

    @Override
    public String authorize(String clientId, String userAccessToken) {
        OAuthCode oAuthCode = new OAuthCode()
                .setExpiresIn(Instant.now().plusSeconds(getDefaultCodeTtlSeconds()))
                .setClientId(clientId)
                .setUserAccessToken(userAccessToken);

        oAuthCodeRepository.save(oAuthCode);

        return oAuthCode.getCode();
    }

    @Override
    public OAuthToken getToken(String clientId, String clientSecret, String authorizationCode) {
        credentialsService.getPrincipalKey(clientId, clientSecret);

        OAuthCode code = oAuthCodeRepository.findByClientIdAndCode(clientId, authorizationCode)
                .orElseThrow(() -> new AuthException("Wrong authorization code"));

        if (code.getExpiresIn().compareTo(Instant.now()) < 0) {
            throw new AuthException("Authorization code expired");
        }

        AuthToken authToken = tokenService.getAuthToken(code.getUserAccessToken());

        OAuthRefreshToken refreshToken = generateRefreshToken(clientId, authToken.getPrincipalId());
        oAuthRefreshTokenRepository.save(refreshToken);

        oAuthCodeRepository.delete(code);

        return new OAuthToken()
                .setAccessToken(authToken.getToken())
                .setTokenType("Bearer")
                .setExpiresIn(authToken.getValidTillMillis())
                .setRefreshToken(refreshToken.getRefreshToken());
    }

    @Override
    public OAuthToken refreshToken(String clientId, String clientSecret, String refreshToken) {
        credentialsService.getPrincipalKey(clientId, clientSecret);

        OAuthRefreshToken oAuthRefreshToken = oAuthRefreshTokenRepository.findByClientIdAndRefreshToken(clientId, refreshToken)
                .orElseThrow(() -> new AuthException("Wrong refresh token"));

        if (oAuthRefreshToken.getExpiresIn().compareTo(Instant.now()) < 0) {
            throw new AuthException("Refresh token expired");
        }

        PrincipalKey principal = principalService.getPrincipalByExternalId(oAuthRefreshToken.getPrincipalId());

        Authentication authentication = authenticationService.authenticateAny(
                tokenService.generateToken(),
                principal,
                null);

        OAuthRefreshToken newRefreshToken = generateRefreshToken(clientId, principal.getExtId());
        oAuthRefreshTokenRepository.save(newRefreshToken);

        return new OAuthToken()
                .setAccessToken(authentication.getToken().getToken())
                .setTokenType("Bearer")
                .setExpiresIn(authentication.getToken().getValidTillMillis())
                .setRefreshToken(newRefreshToken.getRefreshToken());
    }

    private OAuthRefreshToken generateRefreshToken(String clientId, String principalId) {
        return new OAuthRefreshToken()
                .setClientId(clientId)
                .setPrincipalId(principalId)
                .setRefreshToken(generateRefreshToken())
                .setExpiresIn(Instant.now().plusSeconds(getDefaultRefreshTokenTtlSeconds()));
    }

    private String generateRefreshToken() {
        return UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }

    private long getDefaultCodeTtlSeconds() {
        return Duration.ofDays(DEFAULT_CODE_TTL_DAYS).getSeconds();
    }

    private long getDefaultRefreshTokenTtlSeconds() {
        return Duration.ofDays(DEFAULT_REFRESH_TOKEN_TTL_DAYS).getSeconds();
    }
}
