package com.voteva.quiz.repository;

import com.voteva.quiz.model.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResultsRepository extends JpaRepository<ResultEntity, ResultEntity.ResultId> {

    @Query(value = "SELECT * FROM quiz.results WHERE user_uid = ?1", nativeQuery = true)
    List<ResultEntity> findAllByUserUid(UUID userUid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM quiz.results WHERE test_uid = ?1", nativeQuery = true)
    void deleteAllByTestUid(UUID testUid);
}
