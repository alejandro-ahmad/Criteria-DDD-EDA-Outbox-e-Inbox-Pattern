package com.example.demo.categories.infrastructure.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cached_estamentos")
@NoArgsConstructor
public class CachedSocialStrataEntity{
    @Id
    private String id;

    @Column(name = "nombre",nullable = false)
    private String name;

    public CachedSocialStrataEntity(String id,String name){
        this.id = id;
        this.name = name;
    }
}
