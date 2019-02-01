package com.example.model;

import java.util.List;

public interface Property extends Comparable<Property> {
    long getId();

    void setId(long id);

    String getKey();

    void setKey(String key);

    List<String> getValues();

    void setValues(List<String> values);

    void addValue(String value);
}
