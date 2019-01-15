package com.voteva.auth.repository;

import com.voteva.auth.model.entity.OAuthRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OAuthRefreshTokenRepository extends MongoRepository<OAuthRefreshToken, Integer> {

    Optional<OAuthRefreshToken> findByClientIdAndRefreshToken(String clientId, String refreshToken);

}
