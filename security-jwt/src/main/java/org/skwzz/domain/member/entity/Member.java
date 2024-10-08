package org.skwzz.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.skwzz.global.enums.MemberRole;

@Table(name = "SMember")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
