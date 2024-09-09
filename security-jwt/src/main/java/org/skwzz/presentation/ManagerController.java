package org.skwzz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerController {

    @GetMapping
    public String getString(){
        return "THIS IS '/manager' API";
    }
}
