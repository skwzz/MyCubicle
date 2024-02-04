package com.skwzz.sample.service;

import com.skwzz.sample.dto.SampleDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
public class SampleService {


    public String checkDtoInService(@Valid SampleDto sampleDto) {
        return sampleDto.toString();
    }
}
