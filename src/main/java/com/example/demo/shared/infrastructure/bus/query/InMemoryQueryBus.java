package com.example.demo.shared.infrastructure.bus.query;


import com.example.demo.shared.domain.bus.query.*;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryQueryBus implements QueryBus {

    private final Map<Class<? extends Query>, QueryHandler> handlers;

    @SuppressWarnings("rawtypes")
    public InMemoryQueryBus(ApplicationContext context) {
        this.handlers = new HashMap<>();

        Map<String, QueryHandler> matchingBeans = context.getBeansOfType(QueryHandler.class);

        for (QueryHandler handler : matchingBeans.values()) {
            Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handler.getClass(), QueryHandler.class);

            if (generics != null && generics.length > 0) {
                @SuppressWarnings("unchecked")
                Class<? extends Query> queryClass = (Class<? extends Query>) generics[0];
                handlers.put(queryClass, handler);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends Response> R ask(Query query) throws QueryHandlerExecutionError {
        QueryHandler handler = handlers.get(query.getClass());

        if (handler == null) {
            throw new QueryHandlerExecutionError(
                    new IllegalArgumentException("No existe un QueryHandler registrado para: " + query.getClass().getName())
            );
        }

        try {
            return (R) handler.handle(query);
        } catch (Exception e) {
            throw new QueryHandlerExecutionError(e);
        }
    }
}
