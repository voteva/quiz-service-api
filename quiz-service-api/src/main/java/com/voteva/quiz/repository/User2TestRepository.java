package com.voteva.quiz.repository;

import com.voteva.quiz.model.entity.RelUser2TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface User2TestRepository extends JpaRepository<RelUser2TestEntity, RelUser2TestEntity.User2TestId> {

    @Query(value = "SELECT * FROM quiz.rel_user_2_test WHERE user_uid = ?1", nativeQuery = true)
    List<RelUser2TestEntity> findAllByUserUid(UUID userUid);

    @Query(value = "DELETE FROM quiz.rel_user_2_test WHERE test_uid = ?1", nativeQuery = true)
    void removeAllByTestUid(UUID testUid);
}
