package com.voteva.gateway.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voteva.gateway.security.model.AuthenticationToken;
import com.voteva.gateway.security.model.Principal;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WithMockUser(value = "spring")
public class QuizControllerTest {

    private static final String BASE_URL = "/api/v1/quiz/";
    private static final UUID TEST_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final int PERCENT_COMPLETED = 75;

    private Principal principal = new Principal("testing-api", UUID.randomUUID());

    private Gson gson = new GsonBuilder().create();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Before
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(new AuthenticationToken(null, principal, null));
    }

    @Test
    public void testAssignTest() throws Exception {
        AssignTestRequest request = new AssignTestRequest(TEST_UID);

        doNothing().when(quizService).assignTest(request, principal);

        mockMvc.perform(post(BASE_URL + "assign")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUserTests() throws Exception {
        List<QuizInfo> response = new ArrayList<>();

        when(quizService.getTestResults(principal)).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.length()").value(response.size()));
    }

    @Test
    public void testSetTestResults() throws Exception {
        TestResultsRequest request = new TestResultsRequest(TEST_UID, new HashMap<>());
        QuizInfo response = new QuizInfo()
                .setTestUid(TEST_UID)
                .setPercentCompleted(PERCENT_COMPLETED);

        when(quizService.setTestResults(any(TestResultsRequest.class), any(Principal.class))).thenReturn(response);

        mockMvc.perform(post(BASE_URL + "results")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.testUid").value(TEST_UID.toString()))
                .andExpect(jsonPath("$.percentCompleted").value(PERCENT_COMPLETED));
    }
}
