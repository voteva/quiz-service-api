package com.voteva.quiz.repository;

import com.voteva.quiz.model.entity.ObjUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<ObjUserEntity, UUID> { }
