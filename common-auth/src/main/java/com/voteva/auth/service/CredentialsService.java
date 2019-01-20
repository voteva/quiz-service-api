package com.voteva.auth.service;

import com.voteva.auth.model.entity.PrincipalKey;

public interface CredentialsService {

    PrincipalKey getPrincipalKey(String login, String secret);

    void addCredentials(PrincipalKey principal, String login, String secret);
}
