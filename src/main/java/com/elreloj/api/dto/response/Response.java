package com.elreloj.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String status;
    private List<?> messages;
    private List<?> data;
    private UserSignInResponse user_sign_in;

}
