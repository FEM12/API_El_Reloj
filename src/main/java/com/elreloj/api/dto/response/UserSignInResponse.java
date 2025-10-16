package com.elreloj.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignInResponse {

    private String full_name;
    private String email;
    private String token;

}
