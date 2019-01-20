package com.voteva.gateway.redis;

import com.voteva.gateway.redis.model.Task;
import com.voteva.gateway.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final MessagePublisher messagePublisher;
    private final QuizService quizService;

    @Autowired
    public RetryService(
            MessagePublisher messagePublisher,
            QuizService quizService) {
        this.messagePublisher = messagePublisher;
        this.quizService = quizService;
    }

    void retry(Task task, String nextTopicIfFailed) {
        try {
            UUID testUid = UUID.fromString(task.getContent());

            quizService.deleteTestResults(testUid);

        } catch (Exception e) {
            logger.warn("Failed to execute task: " + task.getId());

            messagePublisher.publish(nextTopicIfFailed, task);
        }
    }
}
