package com.voteva.auth.service;

import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.model.entity.FingerPrint;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.model.entity.AuthToken;

public interface AuthenticationService {

    Authentication authenticateAny(
            AuthToken token,
            PrincipalKey principalKey,
            FingerPrint fingerPrint);

    Authentication authenticateService(String serviceId, String serviceSecret);

    Authentication getAuthentication(String token);

    void revokeAuthentication(String token);
}
