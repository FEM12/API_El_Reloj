package com.elreloj.api.service;

import com.elreloj.api.dto.response.CategoryResponse;
import com.elreloj.api.dto.response.ProductResponse;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.model.Product;
import com.elreloj.api.repository.ProductRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRespository productRespository;

    public ResponseEntity<Response> getProductsWithCategory() {

        List<Product> products = productRespository.findAll();
        List<ProductResponse> productListResponse = new ArrayList<>();

        for(int i = 0;i < products.size();i++) {

            CategoryResponse categoryResponse = CategoryResponse.builder()
                .code(products.get(i).getCategory_code().getCode())
                .name(products.get(i).getCategory_code().getName())
                .build();

            ProductResponse productResponse = ProductResponse.builder()
                .sku(products.get(i).getSku())
                .name(products.get(i).getName())
                .price(products.get(i).getPrice())
                .category(categoryResponse)
                .build();

            productListResponse.add(productResponse);

        }

        return ResponseEntity.ok(
            Response.builder()
                .status("Success")
                .data(productListResponse)
                .build()
        );

    }

}
