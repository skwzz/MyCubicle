package org.skwzz.presentation;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 없이 /manager API 호출 시 401 응답 확인")
    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/manager"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("일반 회원가입 후 토큰을 담아 /manager API 호출 시 401 응답 확인")
    @Test
    public void testRegularUserAccess() throws Exception {
        // Sign up as a regular user
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"email\": \"user@example.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk());

        // Sign in to get the token
        MvcResult signInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = signInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        // Call the /manager API with the token
        mockMvc.perform(get("/manager")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @DisplayName("ROLE_MANAGER 회원가입 후 토큰을 담아 /manager API 호출 시 200 응답 확인")
    @Test
    public void testManagerAccess() throws Exception {
        // Sign up as a manager
        mockMvc.perform(post("/auth/staff/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"manager\", \"email\": \"manager@example.com\", \"password\": \"password\", \"role\": \"ROLE_MANAGER\"}"))
                .andExpect(status().isOk());

        // Sign in to get the token
        MvcResult signInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"manager\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = signInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        // Call the /manager API with the token
        mockMvc.perform(get("/manager")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @DisplayName("ROLE_ADMIN 회원가입 후 토큰을 담아 /manager API 호출 시 200 응답 확인")
    @Test
    public void testAdminAccess() throws Exception {
        // Sign up as an admin
        mockMvc.perform(post("/auth/staff/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"email\": \"admin@example.com\", \"password\": \"password\", \"role\": \"ROLE_ADMIN\"}"))
                .andExpect(status().isOk());

        // Sign in to get the token
        MvcResult signInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = signInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        // Call the /manager API with the token
        mockMvc.perform(get("/manager")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
