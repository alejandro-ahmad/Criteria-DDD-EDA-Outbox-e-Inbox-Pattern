package com.example.demo.shared.application.criteria;


import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.criteria.Filter;

import java.util.Map;
import java.util.function.Consumer;


public final class GenericCriteriaValidator {

    private GenericCriteriaValidator() {}

    public static void validate(
            Criteria criteria,
            Map<String, Consumer<String>> allowedFieldValidators
    ) {
        if (criteria == null || !criteria.hasFilters()) {
            return;
        }

        for (Filter filter : criteria.filters().filters()) {
            String field = filter.field().field();
            String rawValue = filter.value().value();

            if (!allowedFieldValidators.containsKey(field)) {
                throw new IllegalArgumentException(
                        String.format("El campo de búsqueda '%s' no está permitido. Campos válidos: %s",
                                      field, allowedFieldValidators.keySet())
                );
            }

            Consumer<String> validator = allowedFieldValidators.get(field);
            if (validator != null) {
                validator.accept(rawValue);
            }
        }
    }
}
