package com.example.demo.shared.domain;

import java.io.Serializable;

public interface Identifier<T> extends Serializable {
    T value();
}
