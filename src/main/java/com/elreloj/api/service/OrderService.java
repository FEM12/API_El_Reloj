package com.elreloj.api.service;

import com.elreloj.api.dto.request.OrderRequest;
import com.elreloj.api.dto.response.Response;
import com.elreloj.api.model.*;
import com.elreloj.api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ExtraRepository extraRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailExtraRepository orderDetailExtraRepository;
    private final ProductRespository productRespository;
    private final UserRepository userRepository;

    private String generateReferenceNumber() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom secureRandom = new SecureRandom();
        Integer[] sections = {8,4,4,4,12};
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0;i < sections.length;i++) {

            for(int j = 0;j < sections[i];j++) {
                stringBuilder.append(characters.charAt(secureRandom.nextInt(characters.length())));
            }

            if(i < sections.length - 1) stringBuilder.append("-");

        }

        return stringBuilder.toString();

    }

    public ResponseEntity<Response> generateOrder(OrderRequest orderRequest) {

        try {

            String ref_number = generateReferenceNumber();

            User user = userRepository.findUserByEmail(orderRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(orderRequest.getEmail()));

            Order order = Order.builder()
                .refNumber(ref_number)
                .user(user)
                .datetime(LocalDateTime.now())
                .methodPay("Cash")
                .total(orderRequest.getTotal())
                .status("Active")
                .build();

            orderRepository.save(order);

            for(int i = 0;i < orderRequest.getOrder_detail().size();i++) {

                Product product = productRespository.findProductBySku(orderRequest.getOrder_detail().get(i).getProduct())
                    .orElseThrow(() -> new EntityNotFoundException(""));

                OrderDetail orderDetail = OrderDetail.builder()
                    .orderRefNumber(order)
                    .productSku(product)
                    .build();

                orderDetailRepository.save(orderDetail);

                for (int j = 0;j < orderRequest.getOrder_detail().get(i).getExtras().size();j++) {

                    Extra extra = extraRepository.findExtraByCode(orderRequest.getOrder_detail().get(i).getExtras().get(j))
                        .orElseThrow(() -> new EntityNotFoundException(""));

                    OrderDetailExtra orderDetailExtra = OrderDetailExtra.builder()
                        .orderDetail(orderDetail)
                        .extraCode(extra)
                        .build();

                    orderDetailExtraRepository.save(orderDetailExtra);

                }

            }

            return ResponseEntity.ok(
                Response.builder()
                    .status("Success")
                    .messages(List.of("Order generated"))
                    .build()
            );

        }
        catch(EntityNotFoundException e) {

            return ResponseEntity.ok(
                Response.builder()
                    .status("Failure")
                    .messages(List.of("Resource not found"))
                    .build()
            );

        }

    }

}