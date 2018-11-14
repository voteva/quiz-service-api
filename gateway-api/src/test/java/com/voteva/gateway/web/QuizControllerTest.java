package com.voteva.gateway.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.web.controller.QuizController;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    private static final UUID USER_UID = UUID.fromString("21793aac-0171-42c1-9c66-7284ec24a330");
    private static final UUID TEST_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final int ATTEMPTS_ALLOWED = 1;
    private static final int PERCENT_COMPLETED = 75;

    private Gson gson = new GsonBuilder().create();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Test
    public void testAssignTest() throws Exception {
        AssignTestRequest request = new AssignTestRequest(USER_UID, TEST_UID, ATTEMPTS_ALLOWED);

        doNothing().when(quizService).assignTest(request);

        mockMvc.perform(post("/api/quiz/assign")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSetTestResults() throws Exception {
        TestResultsRequest request = new TestResultsRequest(USER_UID, TEST_UID, new HashMap<>());
        QuizInfo response = new QuizInfo(TEST_UID, PERCENT_COMPLETED, ATTEMPTS_ALLOWED);

        when(quizService.setTestResults(any(TestResultsRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/quiz/results")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.testUid").value(TEST_UID.toString()))
                .andExpect(jsonPath("$.percentCompleted").value(PERCENT_COMPLETED))
                .andExpect(jsonPath("$.attemptsAllowed").value(ATTEMPTS_ALLOWED));
    }
}
