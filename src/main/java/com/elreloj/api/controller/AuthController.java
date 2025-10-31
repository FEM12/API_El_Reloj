package com.elreloj.api.controller;

import com.elreloj.api.dto.request.UpdatePasswordResquest;
import com.elreloj.api.dto.request.UserSignInRequest;
import com.elreloj.api.dto.request.UserSignUpRequest;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PutMapping("/update_password")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Response> updatePassword(
        Authentication authentication,
        @Valid @RequestBody UpdatePasswordResquest updatePasswordResquest
    ) {

        String email = authentication.getName();
        return authService.updatePassword(email,updatePasswordResquest);

    }

    @GetMapping("/recover_password/{email}")
    public ResponseEntity<Response> recoverPassword(@PathVariable("email") String email) {
        return authService.recoverPassword(email);
    }

}
