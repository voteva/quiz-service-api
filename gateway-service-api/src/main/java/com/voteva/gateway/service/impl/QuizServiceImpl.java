package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.QuizInfoConverter;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.util.GRpcExceptionUtils;
import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.quiz.grpc.model.v1.GGetUserTestsRequest;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    private final GRpcQuizServiceClient rpcQuizServiceClient;

    @Autowired
    public QuizServiceImpl(GRpcQuizServiceClient rpcQuizServiceClient) {
        this.rpcQuizServiceClient = rpcQuizServiceClient;
    }

    @Override
    public List<QuizInfo> getUserTests(UUID userUid) {
        try {
            return rpcQuizServiceClient.getUserTests(
                    GGetUserTestsRequest.newBuilder()
                            .setUserUid(CommonConverter.convert(userUid))
                            .build())
                    .getTestsList().stream()
                    .map(QuizInfoConverter::convert)
                    .collect(Collectors.toList());

        } catch (StatusRuntimeException e) {
            logger.error("Failed to get tests for user with uid={}", userUid);

            throw GRpcExceptionUtils.convert(e);
        }
    }
}
