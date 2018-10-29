package com.voteva.gateway.converter;

import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.quiz.grpc.model.v1.GUser2TestInfo;

public class QuizInfoConverter {

    public static QuizInfo convert(GUser2TestInfo user2TestInfo) {
        return new QuizInfo(
                CommonConverter.convert(user2TestInfo.getTestUid()),
                user2TestInfo.getPercent(),
                user2TestInfo.getAttemptsAllowed());
    }
}
