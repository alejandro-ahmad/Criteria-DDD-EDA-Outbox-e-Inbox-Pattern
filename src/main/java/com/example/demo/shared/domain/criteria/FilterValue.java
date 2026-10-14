package com.example.demo.shared.domain.criteria;

public record FilterValue(String value) {
    public FilterValue {
        if (value == null) {
            throw new IllegalArgumentException("El valor de filtro no puede ser nulo.");
        }
    }
}
