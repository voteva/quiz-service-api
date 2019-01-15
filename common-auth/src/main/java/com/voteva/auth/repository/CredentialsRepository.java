package com.voteva.auth.repository;

import com.voteva.auth.model.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

    Optional<Credentials> findByLogin(String login);
}
