package com.elreloj.api.service;

import com.elreloj.api.config.JwtUtils;
import com.elreloj.api.dto.request.UpdatePasswordResquest;
import com.elreloj.api.dto.request.UserSignInRequest;
import com.elreloj.api.dto.request.UserSignUpRequest;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.dto.response.UserSignInResponse;
import com.elreloj.api.model.User;
import com.elreloj.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDetails userSearch(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(""));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );

    }

    public ResponseEntity<Response> validateUser(UserSignInRequest userSignInRequest) {

        try {

            User user = userRepository.findUserByEmail(userSignInRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Incorrect email"));

            if(!passwordEncoder.matches(userSignInRequest.getPassword(),user.getPassword())) {
                throw new BadCredentialsException("Incorrect password");
            }

            UserSignInResponse userSignInResponse = UserSignInResponse.builder()
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .token(jwtUtils.generateToken(userSearch(user.getEmail())))
                .build();

            return ResponseEntity.status(201).body(
                Response.builder()
                    .status("Success")
                    .user_sign_in(userSignInResponse)
                    .build()
            );

        }
        catch(BadCredentialsException e) {

            return ResponseEntity.status(401).body(
                Response.builder()
                    .status("Failure")
                    .messages(List.of(e.getMessage()))
                    .build()
            );

        }

    }

    public ResponseEntity<Response> signUp(UserSignUpRequest userSignUpRequest) {

        try {

            User user = User.builder()
                .full_name(userSignUpRequest.getFull_name())
                .email(userSignUpRequest.getEmail())
                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                .build();

            userRepository.save(user);

            return ResponseEntity.status(201).body(
                Response.builder()
                    .status("Success")
                    .messages(List.of("User created successfully"))
                    .build()
            );

        }
        catch(Exception e) {

            return ResponseEntity.status(404).body(
                Response.builder()
                    .status("Failure")
                    .messages(List.of("That email is already in use"))
                    .build()
            );

        }

    }

    public ResponseEntity<Response> updatePassword(
        String email,
        UpdatePasswordResquest updatePasswordResquest
    ) {

        try {

            User getUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Incorrect email"));

            User user = getUser.toBuilder()
                .password(passwordEncoder.encode(updatePasswordResquest.getNew_password()))
                .build();

            userRepository.save(user);

            return ResponseEntity.ok(
                Response.builder()
                    .status("Success")
                    .messages(List.of("Password updated successfully"))
                    .build()
            );

        }
        catch(BadCredentialsException e) {

            return ResponseEntity.status(401).body(
                Response.builder()
                    .status("Failure")
                    .messages(List.of(e.getMessage()))
                    .build()
            );

        }

    }

    public ResponseEntity<Response> recoverPassword(String email) {


        try {

            String genericPassword = "P4$$w0rd!";

            User getUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Incorrect email"));

            User user = getUser.toBuilder()
                .password(passwordEncoder.encode(genericPassword))
                .build();

            userRepository.save(user);

            return ResponseEntity.ok(
                Response.builder()
                    .status("Success")
                    .messages(List.of(genericPassword))
                    .build()
            );

        }
        catch(BadCredentialsException e) {

            return ResponseEntity.status(401).body(
                Response.builder()
                    .status("Failure")
                    .messages(List.of(e.getMessage()))
                    .build()
            );

        }

    }

}
