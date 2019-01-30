package com.example.controller;

import com.example.model.DictionaryRecord;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface DictionaryController {
    ModelAndView putProperty(DictionaryRecord property);

    String getDictionary(Model model);

    String getByKey(String key, Model model);

    String getByValue(String value, Model model);

    ModelAndView updateProperty(String key, String oldValue, String newValue);

    ModelAndView removeKey(String key);

    ModelAndView removeProperty(String key, String value);
}
