package com.voteva.auth.service;

import com.voteva.auth.model.entity.Credentials;
import com.voteva.auth.model.entity.PrincipalKey;

public interface CredentialsService {

    PrincipalKey getPrincipalKey(String subsystem, Credentials credentials);
}
