package com.voteva.quiz.repository;

import com.voteva.quiz.model.entity.RelUser2TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User2TestRepository extends JpaRepository<RelUser2TestEntity, RelUser2TestEntity.User2TestId> { }
