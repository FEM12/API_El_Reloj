package com.elreloj.api.service;

import com.elreloj.api.dto.response.CategoryResponse;
import com.elreloj.api.dto.response.ExtraResponse;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.model.Extra;
import com.elreloj.api.repository.ExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtraService {

    private final ExtraRepository extraRepository;

    public ResponseEntity<Response> getExtrasWithCategory() {

        List<Extra> extras = extraRepository.findAll();
        List<ExtraResponse> extraListResonse = new ArrayList<>();

        for(int i = 0;i < extras.size();i++) {

            CategoryResponse categoryResponse = CategoryResponse.builder()
                .code(extras.get(i).getCategory_code().getCode())
                .name(extras.get(i).getCategory_code().getName())
                .build();

            ExtraResponse extraResponse = ExtraResponse.builder()
                .code(extras.get(i).getCode())
                .name(extras.get(i).getName())
                .price(extras.get(i).getPrice())
                .category(categoryResponse)
                .build();

            extraListResonse.add(extraResponse);

        }

        return ResponseEntity.ok(
            Response.builder()
                .status("Success")
                .data(extraListResonse)
                .build()
        );

    }

}
