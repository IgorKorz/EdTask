package com.example.controller;

import com.example.controller.utility.*;
import com.example.model.Property;

import java.util.List;

public interface DictionaryRestController {
    Property putProperty(OneValueRecord record);

    List<Property> getDictionary();

    List<Property> getByKey(String key);

    List<Property> getByValue(String value);

    Property updateProperty(RecordForUpdate record);

    Property removeKey(Key key);

    Property removeProperty(OneValueRecord record);
}
