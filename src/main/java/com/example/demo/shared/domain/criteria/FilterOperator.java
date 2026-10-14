package com.example.demo.shared.domain.criteria;

import java.util.Arrays;

public enum FilterOperator {
    EQUALS("="),NOT_EQUALS("!="),GT(">"),LT("<"),CONTAINS("CONTAINS"),NOT_CONTAINS("NOT_CONTAINS");

    public final String value;

    FilterOperator(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }

    public static FilterOperator fromString(String operator){
        return Arrays.stream(values())
                .filter(op -> op.name().equalsIgnoreCase(operator) || op.value.equalsIgnoreCase(operator))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("El operador: %s no es valido")));
    }
}
