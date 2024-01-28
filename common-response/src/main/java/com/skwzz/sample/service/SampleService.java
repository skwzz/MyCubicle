package com.skwzz.sample.service;

import com.skwzz.sample.dto.SampleDto;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public SampleDto getError() throws Exception {
        throw new Exception("Error Response 확인");
    } 
}
