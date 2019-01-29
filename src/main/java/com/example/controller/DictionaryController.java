package com.example.controller;

import com.example.model.DictionaryRecord;

import org.springframework.ui.Model;

public interface DictionaryController {
    String putProperty(DictionaryRecord property, Model model);

    String getDictionary(Model model);

    String getByKey(String key, Model model);

    String getByValue(String value, Model model);

    String updateProperty(String key, String oldValue, String newValue, Model model);

    String removeKey(String key, Model model);

    String removeProperty(String key, String value, Model model);
}
