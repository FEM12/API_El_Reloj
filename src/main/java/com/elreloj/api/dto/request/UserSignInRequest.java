package com.elreloj.api.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInRequest {

    @Pattern(
        regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
        message = "Enter a valid email (example0108@yahoo.com)"
    )
    private String email;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$",
        message = "Password must have at least 1 capital letter, 8 digits, 1 number, 1 special character"
    )
    private String password;

}
