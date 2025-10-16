package com.elreloj.api.controller;

import com.elreloj.api.dto.request.UserSignInRequest;
import com.elreloj.api.dto.request.UserSignUpRequest;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/users")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign_in")
    public ResponseEntity<Response> signIn(@Valid @RequestBody UserSignInRequest userSignInRequest) {
        return authService.validateUser(userSignInRequest);
    }

    @PostMapping("/sign_up")
    public ResponseEntity<Response> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        return authService.signUp(userSignUpRequest);
    }


}
