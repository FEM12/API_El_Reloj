package com.elreloj.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders", schema = "el_reloj")
public class Order {

    @Id
    @Size(max = 36)
    @Column(name = "ref_number", nullable = false, length = 36)
    private String refNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @NotNull
    @ColumnDefault("'Cash'")
    @Lob
    @Column(name = "method_pay", nullable = false)
    private String methodPay;

    @NotNull
    @Column(name = "total", nullable = false, precision = 5, scale = 2)
    private BigDecimal total;

    @NotNull
    @ColumnDefault("'Active'")
    @Lob
    @Column(name = "status", nullable = false)
    private String status;

}