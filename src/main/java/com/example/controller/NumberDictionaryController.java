package com.example.controller;

import com.example.dao.Dictionary;
import com.example.model.DictionaryRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/number-dictionary")
public class NumberDictionaryController implements DictionaryController {
    private final Dictionary dictionary;

    @Autowired
    public NumberDictionaryController(@Qualifier("numberDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/records")
    public ModelAndView putProperty(@RequestBody DictionaryRecord property) {
        ModelAndView view = new ModelAndView("redirect:/number-dictionary");
        view.addObject("response", dictionary.put(property.getKey(), property.getValues().get(0)));

        return view;
    }

    @Override
    @GetMapping
    public String getDictionary(Model model) {
        model.addAttribute("dictionary", dictionary.getDictionary());

        return "dictionary";
    }

    @Override
    @GetMapping("/values/{key}")
    public String getByKey(@PathVariable String key, Model model) {
        model.addAttribute("dictionary", dictionary.get(key));

        return "dictionary";
    }

    @Override
    @GetMapping("/keys/{value}")
    public String getByValue(@PathVariable String value, Model model) {
        model.addAttribute("dictionary", dictionary.getKeys(value));

        return "dictionary";
    }

    @Override
    @PutMapping(value = "/records", params = { "key", "oldValue", "newValue" })
    public ModelAndView updateProperty(@RequestParam String key,
                                       @RequestParam String oldValue,
                                       @RequestParam String newValue) {
        ModelAndView view = new ModelAndView("redirect:/number-dictionary");
        view.addObject("response", dictionary.update(key, oldValue, newValue));

        return view;
    }

    @Override
    @DeleteMapping(value = "/records", params = "key")
    @ResponseBody
    public ModelAndView removeKey(@RequestParam String key) {
        ModelAndView view = new ModelAndView("redirect:/number-dictionary");
        view.addObject("response", dictionary.removeAll(key));

        return view;
    }

    @Override
    @DeleteMapping(value = "/records", params = { "key", "value" })
    public ModelAndView removeProperty(@RequestParam String key,
                                       @RequestParam String value) {
        ModelAndView view = new ModelAndView("redirect:/number-dictionary");
        view.addObject("response", dictionary.remove(key, value));

        return view;
    }
}
