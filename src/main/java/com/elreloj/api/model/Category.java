package com.elreloj.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories", schema = "el_reloj")
public class Category {

    @Id
    @Size(max = 8)
    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

}