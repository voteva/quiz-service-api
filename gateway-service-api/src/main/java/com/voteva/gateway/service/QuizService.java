package com.voteva.gateway.service;

import com.voteva.gateway.web.to.out.QuizInfo;

import java.util.List;
import java.util.UUID;

public interface QuizService {

    List<QuizInfo> getUserTests(UUID userUid);
}
