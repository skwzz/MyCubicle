package com.skwzz.sample.service;

import com.skwzz.sample.dto.SampleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    private SampleService sampleService;
    
    @Test
    public void DTO_검증_정상데이터_서비스() throws Exception {
        SampleDto sampleDto = SampleDto.builder()
                .name("길동")
                .requiredField("필수데이터")
                .limitedRangeIntField(1)
                .unlimitedRangeIntField(100)
                .build();

        String s = sampleService.checkDtoInService(sampleDto);
        assertEquals(s, sampleDto.toString());
    }

    @Test
    public void DTO_검증_필수값누락_서비스() throws Exception {
        SampleDto sampleDto = SampleDto.builder()
                .name("길동")
                .limitedRangeIntField(100)
                .unlimitedRangeIntField(1)
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            sampleService.checkDtoInService(sampleDto);
        });
    }

    @Test
    public void DTO_검증_숫자범위위반_서비스() throws Exception {
        SampleDto sampleDto = SampleDto.builder()
                .name("길동")
                .requiredField("필수데이터")
                .limitedRangeIntField(100)
                .unlimitedRangeIntField(100)
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            sampleService.checkDtoInService(sampleDto);
        });
    }
}