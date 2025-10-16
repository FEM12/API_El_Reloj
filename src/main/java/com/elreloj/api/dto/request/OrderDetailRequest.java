package com.elreloj.api.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderDetailRequest {

    @Pattern(regexp = "^(PRD)[0-9]{9}$",message = "Wrong sku format")
    private String product;
    private List<@Pattern(regexp = "^(EXT)[0-9]{5}$",message = "Wrong code format") String> extras;

}
