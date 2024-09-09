package org.skwzz.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CloseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String adminToken;
    private String managerToken;
    private String userToken;

    @BeforeEach
    public void setUp() throws Exception {
        // Sign up as an admin
        mockMvc.perform(post("/auth/staff/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"email\": \"admin@example.com\", \"password\": \"password\", \"role\": \"ROLE_ADMIN\"}"))
                .andExpect(status().isOk());

        // Sign in to get the admin token
        MvcResult adminSignInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        adminToken = adminSignInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        // Sign up as a manager
        mockMvc.perform(post("/auth/staff/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"manager\", \"email\": \"manager@example.com\", \"password\": \"password\", \"role\": \"ROLE_MANAGER\"}"))
                .andExpect(status().isOk());

        // Sign in to get the manager token
        MvcResult managerSignInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"manager\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        managerToken = managerSignInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        // Sign up as a user
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"email\": \"user@example.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk());

        // Sign in to get the user token
        MvcResult userSignInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        userToken = userSignInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();
    }

    @DisplayName("토큰 없이 /api/close 호출 시 401 응답 확인")
    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/close"))
                .andDo(print()) // Log the response
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("토큰과 함께 /api/close 호출 시 200 응답 확인")
    @Test
    public void testAuthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/close")
                        .header("Authorization", "Bearer " + adminToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());
    }

    @DisplayName("ROLE_ADMIN, ROLE_MANAGER, ROLE_USER 3명 회원 가입 후 /api/close/required/1 호출 후 각각 레벨에 맞게 200이나 403 상태코드 응답 확인")
    @Test
    public void testRoleLevel1Access() throws Exception {
        // Call the /api/close/required/1 API with admin token
        mockMvc.perform(get("/api/close/required/1")
                        .header("Authorization", "Bearer " + adminToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());

        // Call the /api/close/required/1 API with manager token
        mockMvc.perform(get("/api/close/required/1")
                        .header("Authorization", "Bearer " + managerToken))
                .andDo(print()) // Log the response
                .andExpect(status().isForbidden());

        // Call the /api/close/required/1 API with user token
        mockMvc.perform(get("/api/close/required/1")
                        .header("Authorization", "Bearer " + userToken))
                .andDo(print()) // Log the response
                .andExpect(status().isForbidden());
    }

    @DisplayName("ROLE_ADMIN, ROLE_MANAGER, ROLE_USER 3명 회원 가입 후 /api/close/required/2 호출 후 각각 레벨에 맞게 200이나 403 상태코드 응답 확인")
    @Test
    public void testRoleLevel2Access() throws Exception {
        // Call the /api/close/required/2 API with admin token
        mockMvc.perform(get("/api/close/required/2")
                        .header("Authorization", "Bearer " + adminToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());

        // Call the /api/close/required/2 API with manager token
        mockMvc.perform(get("/api/close/required/2")
                        .header("Authorization", "Bearer " + managerToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());

        // Call the /api/close/required/2 API with user token
        mockMvc.perform(get("/api/close/required/2")
                        .header("Authorization", "Bearer " + userToken))
                .andDo(print()) // Log the response
                .andExpect(status().isForbidden());
    }

    @DisplayName("ROLE_ADMIN, ROLE_MANAGER, ROLE_USER 3명 회원 가입 후 /api/close/required/3 호출 후 각각 레벨에 맞게 200이나 403 상태코드 응답 확인")
    @Test
    public void testRoleLevel3Access() throws Exception {
        // Call the /api/close/required/3 API with admin token
        mockMvc.perform(get("/api/close/required/3")
                        .header("Authorization", "Bearer " + adminToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());

        // Call the /api/close/required/3 API with manager token
        mockMvc.perform(get("/api/close/required/3")
                        .header("Authorization", "Bearer " + managerToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());

        // Call the /api/close/required/3 API with user token
        mockMvc.perform(get("/api/close/required/3")
                        .header("Authorization", "Bearer " + userToken))
                .andDo(print()) // Log the response
                .andExpect(status().isOk());
    }
}
