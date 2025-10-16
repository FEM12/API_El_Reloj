package com.elreloj.api.repository;

import com.elreloj.api.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtraRepository extends JpaRepository<Extra,Integer> {

    Optional<Extra> findExtraByCode(String code);

}
