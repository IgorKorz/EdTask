package com.example.model;

import java.util.List;

public interface Property {
    long getId();

    void setId(long id);

    String getKey();

    void setKey(String key);

    List<String> getValues();

    void addValue(String value);
}
