package com.voteva.auth.service.impl;

import com.voteva.auth.exception.AuthException;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.repository.CredentialsRepository;
import com.voteva.auth.service.CredentialsService;
import com.voteva.auth.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final PrincipalService principalService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialsServiceImpl(
            CredentialsRepository credentialsRepository,
            PrincipalService principalService,
            PasswordEncoder passwordEncoder) {
        this.credentialsRepository = credentialsRepository;
        this.principalService = principalService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PrincipalKey getPrincipalKey(String login, String secret) {
        return credentialsRepository.findByLogin(login)
                .filter(c -> passwordEncoder.matches(secret, c.getSecret()))
                .map(c -> principalService.getPrincipalById(c.getPrincipalId()))
                .orElseThrow(() -> new AuthException("Invalid credentials"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
