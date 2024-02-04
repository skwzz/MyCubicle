package com.skwzz.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skwzz.sample.dto.SampleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void DTO_검증_정상데이터() throws Exception {
        SampleDto sampleDto = SampleDto.builder()
                .name("길동")
                .requiredField("필수데이터")
                .limitedRangeIntField(1)
                .unlimitedRangeIntField(100)
                .build();

        mockMvc.perform(post("/api/v1/sample/check-dto")
                .content(objectMapper.writeValueAsString(sampleDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void DTO_검증_필수값누락() throws Exception {
        SampleDto sampleDto = SampleDto.builder()
                .name("길동")
                .limitedRangeIntField(100)
                .unlimitedRangeIntField(1)
                .build();

        mockMvc.perform(post("/api/v1/sample/check-dto")
                        .content(objectMapper.writeValueAsString(sampleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)))
                .andDo(print());
    }

    @Test
    public void DTO_검증_숫자범위위반() throws Exception {
        SampleDto sampleDto = SampleDto.builder()
                .name("길동")
                .requiredField("필수데이터")
                .limitedRangeIntField(100)
                .unlimitedRangeIntField(100)
                .build();

        mockMvc.perform(post("/api/v1/sample/check-dto")
                        .content(objectMapper.writeValueAsString(sampleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)))
                .andDo(print());
    }
}