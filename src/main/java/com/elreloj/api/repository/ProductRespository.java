package com.elreloj.api.repository;

import com.elreloj.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRespository extends JpaRepository<Product,Integer> {

    Optional<Product> findProductBySku(String sku);

}
