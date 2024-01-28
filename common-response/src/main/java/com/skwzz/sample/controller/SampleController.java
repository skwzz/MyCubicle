package com.skwzz.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class SampleController {

    @GetMapping
    public String getSample(){
        return "sample";
    }
}
