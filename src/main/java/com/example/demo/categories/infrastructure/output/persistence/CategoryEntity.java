package com.example.demo.categories.infrastructure.output.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class CategoryEntity {
    @Id
    private String id;

    @Column(name = "nombre", unique = true)
    @NotNull(message = "El nombre no puede estar vacío")
    private String name;

    @Column(name = "nombre_carnet")
    private String carnetName;

    @Column(name = "estamento_id")
    @NotNull(message = "El estamento no puede estar vacío")
    private String socialStrataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estamento_id", insertable = false, updatable = false)
    private CachedSocialStrataEntity socialStrata;

    @Column(name = "codicate")
    private Integer code;

    public CategoryEntity(){}
}
