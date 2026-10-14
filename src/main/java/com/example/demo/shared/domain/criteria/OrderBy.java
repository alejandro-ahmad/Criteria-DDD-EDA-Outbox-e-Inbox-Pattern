package com.example.demo.shared.domain.criteria;

public record OrderBy(String value) {
    public OrderBy {
        if(value == null ||  value.isBlank()){
            value = "";
        }
    }

    public boolean hasOrder(){
        return !value.isBlank();
    }
}
