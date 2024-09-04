package org.skwzz.domain.member.repository;

import org.skwzz.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByUsernameOrEmail(String username, String email);

}
