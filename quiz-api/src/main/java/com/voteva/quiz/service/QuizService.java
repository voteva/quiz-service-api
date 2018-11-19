package com.voteva.quiz.service;

import com.voteva.quiz.model.entity.ResultEntity;

import java.util.List;
import java.util.UUID;

public interface QuizService {

    void assignTest(UUID userUid, UUID testUid);

    ResultEntity setTestResults(UUID userUid, UUID testUid, int percent);

    List<ResultEntity> getTestResults(UUID userUid);

    void deleteResultsForTest(UUID testUid);
}
