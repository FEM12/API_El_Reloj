package com.elreloj.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ExtraResponse {

    private String code;
    private String name;
    private BigDecimal price;
    private CategoryResponse category;

}
