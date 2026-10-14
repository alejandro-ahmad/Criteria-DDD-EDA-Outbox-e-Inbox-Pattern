package com.example.demo.social_strata.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estamentos")
@Getter
@Setter
@NoArgsConstructor
public class SocialStrataEntity{
    @Id
    private String   id;

    @Column(name = "nombre", unique = true)
    private String name;
}
