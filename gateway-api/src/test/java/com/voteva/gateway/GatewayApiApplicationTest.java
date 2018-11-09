package com.voteva.gateway;

import com.voteva.gateway.web.controller.QuizController;
import com.voteva.gateway.web.controller.TestsController;
import com.voteva.gateway.web.controller.UsersController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GatewayApiApplicationTest {

    private final QuizController quizController;
    private final TestsController testsController;
    private final UsersController usersController;

    @Autowired
    GatewayApiApplicationTest(QuizController quizController,
                              TestsController testsController,
                              UsersController usersController) {
        this.quizController = quizController;
        this.testsController = testsController;
        this.usersController = usersController;
    }

    @Test
    void testApplicationStarted() {
        assertNotNull(quizController);
        assertNotNull(testsController);
        assertNotNull(usersController);
    }
}
