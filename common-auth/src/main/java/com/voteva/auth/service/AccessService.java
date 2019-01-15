package com.voteva.auth.service;

public interface AccessService {

    void checkAccess(String token, String grant);
}
