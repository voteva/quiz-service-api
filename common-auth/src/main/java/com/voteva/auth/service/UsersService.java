package com.voteva.auth.service;

import java.util.UUID;

public interface UsersService {

    UUID addUser(String email, String password);
}
