package com.elreloj.api.controller;

import com.elreloj.api.dto.request.OrderRequest;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Response> generateOrder(
        Authentication authentication,
        @Valid @RequestBody OrderRequest orderRequest
    ) {

        String email = authentication.getName();
        return orderService.generateOrder(orderRequest);

    }

}