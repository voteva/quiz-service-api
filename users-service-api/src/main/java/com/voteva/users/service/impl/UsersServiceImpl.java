package com.voteva.users.service.impl;

import com.voteva.users.model.entity.ObjUserEntity;
import com.voteva.users.repository.UserRepository;
import com.voteva.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID addUser(String email, String password) {
        ObjUserEntity userEntity = new ObjUserEntity()
                .setUserEmail(email)
                .setUserPassword(password);

        return userRepository.save(userEntity).getUserUid();
    }
}
