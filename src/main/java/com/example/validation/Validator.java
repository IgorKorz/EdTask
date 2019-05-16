package com.example.validation;

import com.example.model.DictionaryType;
import com.example.model.Property;

public interface Validator {
    Property getResult();

    boolean isValidRecord(String key, String value, int keyLength, String keyRegex);

    boolean keyExists(String key, DictionaryType type);

    boolean recordExists(String key, String value, DictionaryType type);
}