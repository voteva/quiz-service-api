package com.voteva.quiz.service.impl;

import com.voteva.quiz.model.entity.ResultEntity;
import com.voteva.quiz.repository.ResultsRepository;
import com.voteva.quiz.service.QuizService;
import com.voteva.quiz.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService {

    private final ResultsRepository resultsRepository;
    private final UsersService usersService;

    @Autowired
    public QuizServiceImpl(
            ResultsRepository resultsRepository,
            UsersService usersService) {
        this.resultsRepository = resultsRepository;
        this.usersService = usersService;
    }

    @Override
    public void assignTest(UUID userUid, UUID testUid) {
        usersService.getUser(userUid);

        resultsRepository.save(
                new ResultEntity()
                        .setResultId(new ResultEntity.ResultId(userUid, testUid)));
    }

    @Override
    public ResultEntity setTestResults(UUID userUid, UUID testUid, int percent) {
        ResultEntity resultEntity = resultsRepository
                .findById(new ResultEntity.ResultId(userUid, testUid))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("Test=%s is not assigned to user=%s", testUid, userUid)));

        resultEntity.setPercent(percent);

        return resultsRepository.save(resultEntity);
    }

    @Override
    public List<ResultEntity> getTestResults(UUID userUid) {
        return resultsRepository.findAllByUserUid(userUid);
    }

    @Override
    public void deleteResultsForTest(UUID testUid) {
        resultsRepository.deleteAllByTestUid(testUid);
    }
}
