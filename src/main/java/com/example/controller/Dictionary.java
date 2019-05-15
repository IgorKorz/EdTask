package com.example.controller;

import com.example.model.Property;

import java.util.List;

public interface Dictionary {
    Property put(String key, String value);

    String getName();

    List<Property> getDictionary();

    List<Property> get(String key);

    Property remove(String key, String value);

    void initDictionary();
}
