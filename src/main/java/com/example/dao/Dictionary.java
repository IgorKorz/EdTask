package com.example.dao;

import com.example.model.Property;

import java.util.List;

public interface Dictionary {
    Property put(String key, String value);

    String getName();

    List<Property> getDictionary();

    List<Property> get(String key);

    List<Property> getKeys(String value);

    Property update(String key, String oldValue, String newValue);

    Property remove(String key, String value);

    Property removeAll(String key);
}
