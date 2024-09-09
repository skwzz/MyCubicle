package org.skwzz.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skwzz.domain.member.repository.MemberRepository;
import org.skwzz.global.util.JwtUtil;
import org.skwzz.payload.request.SignInRequestDTO;
import org.skwzz.payload.response.SignInResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService{

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignInResponseDTO signIn(SignInRequestDTO request) {
        memberRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String role = authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElseThrow(() -> new RuntimeException("권한정보가 없습니다."));
        String token = jwtUtil.generateToken(request.getUsername(), role);
        log.info("GENERATED TOKEN: {}", token);
        return new SignInResponseDTO(token);
    }
}
