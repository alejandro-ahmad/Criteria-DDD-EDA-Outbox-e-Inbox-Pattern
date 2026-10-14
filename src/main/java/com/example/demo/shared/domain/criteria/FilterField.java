package com.example.demo.shared.domain.criteria;

public record FilterField(String field) {
    public FilterField {
        if(field == null){
            throw new IllegalArgumentException("El campo no puede estar vacio.");
        }
    }
}
