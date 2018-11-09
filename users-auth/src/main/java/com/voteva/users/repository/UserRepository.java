package com.voteva.users.repository;

import com.voteva.users.model.entity.ObjUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ObjUserEntity, Integer> {

    Optional<ObjUserEntity> findByUserEmail(String userEmail);
}
