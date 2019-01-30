package com.example.controller;

import com.example.controller.utility.*;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface DictionaryController {
    ModelAndView putProperty(OneValueRecord record);

    String getDictionary(Model model);

    String getByKey(String key, Model model);

    String getByValue(String value, Model model);

    ModelAndView updateProperty(RecordForUpdate record);

    ModelAndView removeKey(Key key);

    ModelAndView removeProperty(OneValueRecord record);
}
