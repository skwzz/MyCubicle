package org.skwzz.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignInResponseDTO {
    private String token;
}
