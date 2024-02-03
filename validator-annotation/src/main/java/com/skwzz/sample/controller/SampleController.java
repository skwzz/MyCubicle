package com.skwzz.sample.controller;

import com.skwzz.sample.dto.SampleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {

    @GetMapping
    public String getSample(){
        return "sample";
    }

    @PostMapping("/check-dto")
    public String checkDto(@Valid @RequestBody SampleDto sampleDto){
        log.info("SAMPLE_DTO TOSTRING()");
        log.info(sampleDto.toString());
        return sampleDto.toString();
    }
}
