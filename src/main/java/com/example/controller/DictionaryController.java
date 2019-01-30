package com.example.controller;

import com.example.entity.PropertyKey;
import com.example.controller.utility.OneValueRecord;

import com.example.controller.utility.RecordForUpdate;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface DictionaryController {
    ModelAndView putProperty(OneValueRecord record);

    String getDictionary(Model model);

    String getByKey(String key, Model model);

    String getByValue(String value, Model model);

    ModelAndView updateProperty(RecordForUpdate record);

    ModelAndView removeKey(PropertyKey key);

    ModelAndView removeProperty(OneValueRecord record);
}
