package com.elreloj.api.controller;

import com.elreloj.api.dto.response.Response;
import com.elreloj.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Response> getProductsWithCategory(Authentication authentication) {

        String email = authentication.getName();
        return productService.getProductsWithCategory();

    }

}