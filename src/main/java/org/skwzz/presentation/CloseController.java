package org.skwzz.presentation;

import lombok.RequiredArgsConstructor;
import org.skwzz.global.annotation.RoleLevel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/close")
public class CloseController {

    @GetMapping
    public String sample() {
        return "This is GET /api/close - require authenticate";
    }

    @RoleLevel(1)
    @GetMapping("/required/1")
    public String requiredRoleLevel1() {
        return "This is GET /api/close - Authenticated and required Role Level 1";
    }

    @RoleLevel(2)
    @GetMapping("/required/2")
    public String requiredRoleLevel2() {
        return "This is GET /api/close - Authenticated and required Role Level 2";
    }

    @RoleLevel(3)
    @GetMapping("/required/3")
    public String requiredRoleLevel3() {
        return "This is GET /api/close - Authenticated and required Role Level 3";
    }
}
