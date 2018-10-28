package com.voteva.users.service;

import java.util.UUID;

public interface UsersService {

    UUID addUser(String email, String password);
}
