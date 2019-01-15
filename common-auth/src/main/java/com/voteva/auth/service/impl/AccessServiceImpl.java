package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.repository.AccessRepository;
import com.voteva.auth.service.AccessService;
import com.voteva.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    private final AccessRepository accessRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public AccessServiceImpl(
            AccessRepository accessRepository,
            AuthenticationService authenticationService) {

        this.accessRepository = accessRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public void checkAccess(String token, String grant) {
        Authentication authentication = authenticationService.getAuthentication(token);

        accessRepository.findAllByPrincipalId(authentication.getPrincipalKey().getId())
                .stream()
                .filter(a -> grant.equals(a.getGrant()))
                .findFirst()
                .orElseThrow(() -> new AuthException("Insufficient permissions"));
    }
}
