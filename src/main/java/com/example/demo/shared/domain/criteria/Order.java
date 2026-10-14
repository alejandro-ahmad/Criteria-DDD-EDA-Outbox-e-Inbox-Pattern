package com.example.demo.shared.domain.criteria;

import java.util.Optional;

public record Order(OrderBy orderBy, OrderType orderType) {

    public static Order none(){
        return new Order(new OrderBy(""),OrderType.NONE);
    }

    public static Order desc(String orderBy){
        return new Order(new OrderBy(orderBy),OrderType.DESC);
    }

    public static Order asc(String orderBy){
        return new Order(new OrderBy(orderBy),OrderType.ASC);
    }

    public static Order fromValues(Optional<String> orderBy,Optional<String> orderType){
        return orderBy
                .filter(field -> !field.isBlank())
                .map(field -> new Order(
                        new OrderBy(field),
                        OrderType.fromString(orderType.orElse("ASC"))
                )).orElseGet(Order::none);
    }

    public boolean hasOrder(){
        return !orderType().isNone();
    }
}
