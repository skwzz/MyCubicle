package org.skwzz.domain.member.repository;

import org.skwzz.domain.member.entity.SMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMemberRepository extends JpaRepository<SMember, Long> {
}
