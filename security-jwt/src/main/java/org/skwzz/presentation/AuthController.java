package org.skwzz.presentation;

import lombok.RequiredArgsConstructor;
import org.skwzz.domain.member.service.AuthService;
import org.skwzz.domain.member.service.MemberService;
import org.skwzz.payload.request.StaffSignUpRequestDTO;
import org.skwzz.payload.request.SignInRequestDTO;
import org.skwzz.payload.request.SignUpRequestDTO;
import org.skwzz.payload.response.SignInResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpRequestDTO request){
        return ResponseEntity.ok().body(memberService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO request){
        return ResponseEntity.ok().body(authService.signIn(request));
    }

    @PostMapping("/staff/sign-up")
    public ResponseEntity<Boolean> staffSignUp(@RequestBody StaffSignUpRequestDTO request){
        return ResponseEntity.ok().body(memberService.signUp(request));
    }
}
