package com.voteva.interview.service;

import com.voteva.interview.model.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InterviewService {

    Page<QuestionEntity> getAllQuestions(Pageable pageable);

    Page<QuestionEntity> getQuestionsByCategory(String category, Pageable pageable);

    List<String> getCategories();

    QuestionEntity getQuestion(UUID testUid);

    UUID addQuestion(QuestionEntity entity);

    void removeQuestion(UUID questionUid);
}
