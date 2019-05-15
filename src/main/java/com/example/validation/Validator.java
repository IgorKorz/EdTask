package com.example.validation;

import com.example.model.Property;

public interface Validator {
    Property getResult();

    boolean isValidRecord(String key, String value);

    boolean keyExists(String key);

    boolean recordExists(String key, String value);
}