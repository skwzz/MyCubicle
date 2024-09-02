package org.skwzz.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthController {

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(){
        return ResponseEntity.ok().body(true);
    }
}
