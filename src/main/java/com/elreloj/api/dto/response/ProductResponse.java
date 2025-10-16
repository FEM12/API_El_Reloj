package com.elreloj.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductResponse {

    private String sku;
    private String name;
    private BigDecimal price;
    private CategoryResponse category;

}
