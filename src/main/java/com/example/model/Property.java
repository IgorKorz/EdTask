package com.example.model;

public interface Property {
    String getKey();

    void setKey(String key);

    String getValue();

    void setValue(String value);

    DictionaryType getType();

    void setType(DictionaryType type);
}
