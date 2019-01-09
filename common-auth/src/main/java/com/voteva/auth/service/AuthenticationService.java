package com.voteva.auth.service;

import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.model.entity.FingerPrint;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.model.entity.Token;

public interface AuthenticationService {

    Authentication authenticateAny(
            Token token,
            PrincipalKey principalKey,
            FingerPrint fingerPrint);

    Authentication getAuthentication(String token);
}
