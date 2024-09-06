package org.skwzz.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skwzz.domain.member.entity.Member;
import org.skwzz.domain.member.repository.MemberRepository;
import org.skwzz.payload.request.SignUpRequestDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_ShouldThrowException_WhenUsernameOrEmailExists() {
        // Given
        // 밑의 SignUpRequestDTO 생성자 코드를 builder 패턴으로 변경해줘
        SignUpRequestDTO request = new SignUpRequestDTO("existingUser", "existingEmail", "password");
        when(memberRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()))
                .thenReturn(Optional.of(new Member()));

        // When & Then
        assertThrows(RuntimeException.class, () -> memberService.signUp(request));
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void signUp_ShouldSaveMember_WhenUsernameOrEmailDoesNotExist() {
        // Given
        SignUpRequestDTO request = new SignUpRequestDTO("newUser", "newEmail", "password");
        when(memberRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()))
                .thenReturn(Optional.empty());

        // When
        memberService.signUp(request);

        // Then
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}
