package com.elreloj.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extras", schema = "el_reloj")
public class Extra {

    @Id
    @Size(max = 8)
    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false, precision = 4, scale = 2)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_code", nullable = false)
    private Category category_code;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

}