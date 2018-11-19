package com.voteva.interview.repository;

import com.voteva.interview.model.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionsRepository extends JpaRepository<QuestionEntity, Integer> {

    Optional<QuestionEntity> findByQuestionUid(UUID questionUid);

    Page<QuestionEntity> findByCategory(String category, Pageable pageable);

    void deleteByQuestionUid(UUID questionUid);
}
