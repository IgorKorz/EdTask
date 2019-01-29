package com.example.controller;

import com.example.model.DictionaryRecord;
import com.example.model.Property;

import java.util.List;

public interface DictionaryRestController {
    Property putProperty(DictionaryRecord property);

    List<Property> getDictionary();

    List<Property> getByKey(String key);

    List<Property> getByValue(String value);

    Property updateProperty(String key, String oldValue, String newValue);

    Property removeKey(String key);

    Property removeProperty(String key, String value);
}
