package com.voteva.auth.service.impl;

import com.voteva.auth.repository.UserRepository;
import com.voteva.auth.service.UsersService;
import com.voteva.auth.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UUID addUser(String email, String password) {
        userRepository.findByUserEmail(email)
                .ifPresent(e -> {
                    throw new IllegalArgumentException(String.format("User=%s already exists", email));
                });

        UserEntity userEntity = new UserEntity()
                .setUserEmail(email)
                .setUserPassword(passwordEncoder.encode(password));

        return userRepository.save(userEntity).getUserUid();
    }
}
