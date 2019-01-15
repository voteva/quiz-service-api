package com.voteva.auth.repository;

import com.voteva.auth.model.entity.OAuthCode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OAuthCodeRepository extends MongoRepository<OAuthCode, Integer> {

    Optional<OAuthCode> findByClientIdAndCode(String clientId, String code);

}
