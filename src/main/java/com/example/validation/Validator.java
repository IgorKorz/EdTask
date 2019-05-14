package com.example.validation;

import com.example.model.Property;

public interface Validator {
    Property getResult();

    boolean isValidKey(String key);

    boolean isValidValue(String value);

    int containsKey(String key);
}