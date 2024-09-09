package org.skwzz.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.skwzz.global.enums.MemberRole;

@Setter
@Getter
public class StaffSignUpRequestDTO extends SignUpRequestDTO{

    private MemberRole role;

    public StaffSignUpRequestDTO(String username, String email, String password, MemberRole role) {
        super(username, email, password);
        this.role = role;
    }
}

