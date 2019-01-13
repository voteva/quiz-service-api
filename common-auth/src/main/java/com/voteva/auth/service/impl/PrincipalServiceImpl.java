package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.repository.PrincipalRepository;
import com.voteva.auth.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;

    @Autowired
    public PrincipalServiceImpl(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    @Override
    public PrincipalKey getPrincipalById(int id) {
        return principalRepository.findById(id)
                .orElseThrow(() -> new AuthException("Principal not found"));
    }

    @Override
    public PrincipalKey getPrincipalByExternalId(String externalId) {
        return principalRepository.findByExtId(externalId)
                .orElseThrow(() -> new AuthException("Principal not found"));
    }
}
