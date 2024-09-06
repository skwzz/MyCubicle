package org.skwzz.presentation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 테스트 메소드 실행 후에 컨텍스트를 강제로 dirty 상태로 만들어 다음 테스트 메소드 실행 시 새로운 컨텍스트를 만들도록 함
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원가입 > 로그인 > 토큰 담아서 API 호출")
    @Test
    public void scenario1() throws Exception {
        // Sign up
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"email\": \"testEmail@example.com\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk());

        // Sign in
        MvcResult signInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = signInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        mockMvc.perform(get("/api/sample")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원가입 > 로그인 > 토큰 만료시간까지 대기 > 토큰 담아서 API 호출")
    @Test
    public void scenario2() throws Exception {
        // Sign up
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"email\": \"testEmail@example.com\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk());

        // Sign in
        MvcResult signInResult = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = signInResult.getResponse().getContentAsString().split(":")[1].replace("\"", "").replace("}", "").trim();

        Thread.sleep(3100);

        mockMvc.perform(get("/api/sample")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
