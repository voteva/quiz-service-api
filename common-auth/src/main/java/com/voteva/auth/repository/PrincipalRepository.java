package com.voteva.auth.repository;

import com.voteva.auth.model.entity.PrincipalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrincipalRepository extends JpaRepository<PrincipalKey, Integer> {

    Optional<PrincipalKey> findByExtId(String externalId);
}
