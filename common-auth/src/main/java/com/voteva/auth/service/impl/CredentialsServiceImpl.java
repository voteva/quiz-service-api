package com.voteva.auth.service.impl;

import com.voteva.auth.model.entity.Credentials;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.service.CredentialsService;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Override
    public PrincipalKey getPrincipalKey(String subsystem, Credentials credentials) {
        // check login & password

        return new PrincipalKey("123", "123");
    }
}
