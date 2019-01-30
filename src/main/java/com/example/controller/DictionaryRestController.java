package com.example.controller;

import com.example.entity.PropertyKey;
import com.example.controller.utility.OneValueRecord;
import com.example.model.Property;
import com.example.controller.utility.RecordForUpdate;

import java.util.List;

public interface DictionaryRestController {
    Property putProperty(OneValueRecord record);

    List<Property> getDictionary();

    List<Property> getByKey(String key);

    List<Property> getByValue(String value);

    Property updateProperty(RecordForUpdate record);

    Property removeKey(PropertyKey key);

    Property removeProperty(OneValueRecord record);
}
