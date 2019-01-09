package com.voteva.gateway.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voteva.gateway.service.AuthenticationService;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.UserUidRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
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
public class UsersControllerTest {

    private static final UUID USER_UID = UUID.fromString("21793aac-0171-42c1-9c66-7284ec24a330");
    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "$eCuReP@$$wOrD";
    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Ivanov";

    private Gson gson = new GsonBuilder().create();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private QuizService quizService;

    @Test
    public void testGetUsers() throws Exception {
        PagedResult<UserInfo> response = new PagedResult<>(1,
                Collections.singletonList(new UserInfo()
                        .setUserUid(USER_UID)
                        .setFirstName(FIRST_NAME)
                        .setLastName(LAST_NAME)
                        .setAdmin(false)
                        .setBlocked(false)));

        when(quizService.getUsers(0, 20)).thenReturn(response);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.totalCount").value(response.getTotalCount()))
                .andExpect(jsonPath("$.items.length()").value(response.getTotalCount()))
                .andExpect(jsonPath("$.items[0].userUid").value(response.getItems().get(0).getUserUid().toString()))
                .andExpect(jsonPath("$.items[0].firstName").value(response.getItems().get(0).getFirstName()))
                .andExpect(jsonPath("$.items[0].lastName").value(response.getItems().get(0).getLastName()));
    }

    @Test
    public void testGetUserInfo() throws Exception {
        UserInfo response = new UserInfo()
                .setUserUid(USER_UID)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setAdmin(false)
                .setBlocked(false);

        when(quizService.getUserByUid(USER_UID)).thenReturn(response);

        mockMvc.perform(get("/api/users/{uuid}", USER_UID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.userUid").value(response.getUserUid().toString()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.isAdmin").value(response.isAdmin()))
                .andExpect(jsonPath("$.isBlocked").value(response.isBlocked()));
    }

    @Test
    public void testAddUser() throws Exception {
        AddUserRequest request = new AddUserRequest(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME);

        Instant now = Instant.now();
        AddUserInfo response = new AddUserInfo(USER_UID, now.toEpochMilli());

        when(quizService.addUser(any(AddUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.userUid").value(response.getUserUid().toString()))
                .andExpect(jsonPath("$.createdDateTime").value(response.getCreatedDateTime()));
    }

    @Test
    public void testSetAdminGrants() throws Exception {
        UserUidRequest request = new UserUidRequest(USER_UID);

        doNothing().when(quizService).setAdminGrants(request.getUserUid());

        mockMvc.perform(post("/api/users/admin")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testRemoveAdminGrants() throws Exception {
        UserUidRequest request = new UserUidRequest(USER_UID);

        doNothing().when(quizService).removeAdminGrants(request.getUserUid());

        mockMvc.perform(post("/api/users/remove-admin")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testBlockUser() throws Exception {
        UserUidRequest request = new UserUidRequest(USER_UID);

        doNothing().when(quizService).blockUser(request.getUserUid());

        mockMvc.perform(post("/api/users/block")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUnblockUser() throws Exception {
        UserUidRequest request = new UserUidRequest(USER_UID);

        doNothing().when(quizService).unblockUser(request.getUserUid());

        mockMvc.perform(post("/api/users/unblock")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isNoContent());
    }
}
