package com.voteva.auth.repository;

import com.voteva.auth.model.entity.PrincipalAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<PrincipalAccess, Integer> {

    List<PrincipalAccess> findAllByPrincipalId(int principalId);
}
