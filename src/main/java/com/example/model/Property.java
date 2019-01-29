package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface Property {
    long getId();

    void setId(long id);

    String getKey();

    void setKey(String key);

    List<String> getValues();

    void setValues(List<String> values);

    void addValue(String value);
}
