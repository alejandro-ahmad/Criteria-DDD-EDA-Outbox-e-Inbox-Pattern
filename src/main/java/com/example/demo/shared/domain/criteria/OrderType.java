package com.example.demo.shared.domain.criteria;

public enum OrderType {
    ASC("ASC"),
    DESC("DESC"),
    NONE("NONE");

    public final String value;

    OrderType(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }

    public boolean isNone(){
        return this == NONE;
    }

    public boolean isAsc(){
        return this == ASC;
    }

    public static OrderType fromString(String type){
        if(type == null || type.isBlank()){
            return DESC;
        }
        try{
            return valueOf(type.toUpperCase());
        } catch(IllegalArgumentException e){
            return ASC;
        }
    }
}
