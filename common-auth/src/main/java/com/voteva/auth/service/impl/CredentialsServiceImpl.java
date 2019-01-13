package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.repository.CredentialsRepository;
import com.voteva.auth.service.CredentialsService;
import com.voteva.auth.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final PrincipalService principalService;

    @Autowired
    public CredentialsServiceImpl(
            CredentialsRepository credentialsRepository,
            PrincipalService principalService) {
        this.credentialsRepository = credentialsRepository;
        this.principalService = principalService;
    }

    @Override
    public PrincipalKey getPrincipalKey(String login, String secret) {
        return credentialsRepository.findByLoginAndSecret(login, secret)
                .map(c -> principalService.getPrincipalById(c.getPrincipalId()))
                .orElseThrow(() -> new AuthException("Invalid credentials"));
    }
}
