package com.voteva.auth.service;

import com.voteva.auth.model.entity.PrincipalKey;

public interface PrincipalService {

    PrincipalKey getPrincipalById(int id);

    PrincipalKey getPrincipalByExternalId(String externalId);
}
