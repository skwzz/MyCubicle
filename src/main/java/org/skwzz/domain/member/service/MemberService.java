package org.skwzz.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skwzz.domain.member.entity.Member;
import org.skwzz.domain.member.repository.MemberRepository;
import org.skwzz.payload.request.SignUpRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Boolean signUp(SignUpRequestDTO request) {
        memberRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).ifPresentOrElse(
                existedMember -> {
                    throw new RuntimeException("이미 존재하는 아이디/이메일 입니다.");
                },
                () -> {
                    Member member = Member.builder()
                            .username(request.getUsername())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .email(request.getEmail())
                            .build();
                    memberRepository.save(member);
                }
        );
        return true;
    }
}
