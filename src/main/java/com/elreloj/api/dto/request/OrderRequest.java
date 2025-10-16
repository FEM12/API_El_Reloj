package com.elreloj.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class OrderRequest {


    @Pattern(
        regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,6}$",
        message = "Enter a valid email (example0108@yahoo.com)"
    )
    private String email;

    @NotEmpty(message = "The list cannot be empty")
    private List<@Valid OrderDetailRequest> order_detail;

    @NotNull(message = "Falta el campo 'total'")
    @Digits(integer = 3,fraction = 2,message = "Number of digits exceeded")
    @DecimalMin(value = "0",message = "Only quantities greater than zero are accepted")
    @DecimalMax(value = "999.99",message = "Only quantities less than 1000 are accepted")
    private BigDecimal total;

}
