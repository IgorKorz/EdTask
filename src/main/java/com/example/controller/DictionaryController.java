package com.example.controller;

import com.example.controller.utility.*;

import org.springframework.ui.Model;

public interface DictionaryController {
    String putProperty(OneValueRecord record, Model model);

    String getDictionary(Model model);

    String getByKey(String key, Model model);

    String getByValue(String value, Model model);

    String updateProperty(RecordForUpdate record, Model model);

    String removeKey(Key key);

    String removeProperty(OneValueRecord record);
}
