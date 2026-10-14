package com.example.demo.categories.application.query.by_id;

import com.example.demo.shared.domain.bus.query.Query;

public record CategoryByIdQuery(String id) implements Query {
}
