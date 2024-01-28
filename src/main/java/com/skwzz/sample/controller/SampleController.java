package com.skwzz.sample.controller;

import com.skwzz.sample.dto.SampleDto;
import com.skwzz.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/sample")
@RestController
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/single")
    public SampleDto getSample(){
        return SampleDto.builder()
                .col1("컬럼 1")
                .col2("컬럼 2")
                .build();
    }

    @GetMapping("/list")
    public List<SampleDto> getSampleList(){
        List<SampleDto> sampleDtoList = new ArrayList<>();
        SampleDto sampleDto = SampleDto.builder()
                .col1("컬럼 1")
                .col2("컬럼 2")
                .build();
        for(int i=0; i<10; i++){
            sampleDtoList.add(sampleDto);
        }
        return sampleDtoList;
    }

    @GetMapping("/error")
    public SampleDto getError() throws Exception {
        SampleDto dto = sampleService.getError();
        return SampleDto.builder()
                .col1("컬럼 1")
                .col2("컬럼 2")
                .build();
    }
}
