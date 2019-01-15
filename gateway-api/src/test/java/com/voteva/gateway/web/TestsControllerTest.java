package com.voteva.gateway.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.gateway.web.to.out.TestInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WithMockUser(value = "spring")
public class TestsControllerTest {

    private static final String BASE_URL = "/api/v1/tests/";
    private static final UUID TEST_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final String TEST_NAME = "Java Test";
    private static final String CATEGORY = "Java";

    private Gson gson = new GsonBuilder().create();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestsService testsService;

    @Test
    public void testGetTests() throws Exception {
        PagedResult<TestInfo> response = new PagedResult<>(1,
                Collections.singletonList(new TestInfo(TEST_UID, TEST_NAME, CATEGORY, new ArrayList<>())));

        when(testsService.getTests(null, 0, 20)).thenReturn(response);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.totalCount").value(response.getTotalCount()))
                .andExpect(jsonPath("$.items.length()").value(response.getTotalCount()))
                .andExpect(jsonPath("$.items[0].testUid").value(response.getItems().get(0).getTestUid().toString()))
                .andExpect(jsonPath("$.items[0].testName").value(response.getItems().get(0).getTestName()))
                .andExpect(jsonPath("$.items[0].testCategory").value(response.getItems().get(0).getTestCategory()));
    }

    @Test
    public void testGetTestInfo() throws Exception {
        TestInfo response = new TestInfo(TEST_UID, TEST_NAME, CATEGORY, new ArrayList<>());

        when(testsService.getTestInfo(TEST_UID)).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "{uuid}", TEST_UID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.testUid").value(response.getTestUid().toString()))
                .andExpect(jsonPath("$.testName").value(response.getTestName()))
                .andExpect(jsonPath("$.testCategory").value(response.getTestCategory()));
    }

    @Test
    public void testAddTest() throws Exception {
        AddTestRequest request = new AddTestRequest(TEST_NAME, CATEGORY, new ArrayList<>());

        when(testsService.addTest(any(AddTestRequest.class))).thenReturn(TEST_UID);

        mockMvc.perform(post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.testUid").value(TEST_UID.toString()));
    }

    @Test
    public void testDeleteTest() throws Exception {
        doNothing().when(testsService).deleteTest(TEST_UID);

        mockMvc.perform(delete(BASE_URL + TEST_UID))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetCategories() throws Exception {
        List<String> response = Collections.singletonList(CATEGORY);

        when(testsService.getTestCategories()).thenReturn(response);

        mockMvc.perform(get(BASE_URL + "categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.length()").value(response.size()))
                .andExpect(jsonPath("$[0]").value(response.get(0)));
    }
}
