package com.voteva.auth.repository;

import com.voteva.auth.model.entity.AuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<AuthToken, Integer> {

    Optional<AuthToken> findByToken(String token);
}
