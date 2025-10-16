package com.elreloj.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private String code;
    private String name;

}
