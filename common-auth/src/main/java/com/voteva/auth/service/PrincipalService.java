package com.voteva.auth.service;

import com.voteva.auth.model.entity.PrincipalKey;

public interface PrincipalService {

    PrincipalKey getPrincipalById(int id);

    PrincipalKey getPrincipalByExternalId(String externalId);

    PrincipalKey addPrincipal(String externalId);

    void deletePrincipal(String externalId);
}
