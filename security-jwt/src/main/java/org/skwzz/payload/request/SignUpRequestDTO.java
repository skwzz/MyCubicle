package org.skwzz.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpRequestDTO {

    private String username;
    private String email;
    private String password;
}
