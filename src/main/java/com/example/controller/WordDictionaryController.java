package com.example.controller;

import com.example.dao.Dictionary;
import com.example.entity.PropertyKey;
import com.example.controller.utility.OneValueRecord;
import com.example.controller.utility.RecordForUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/word-dictionary")
public class WordDictionaryController implements DictionaryController {
    private final Dictionary dictionary;
    private final String controllerPath = "/word-dictionary";

    @Autowired
    public WordDictionaryController(@Qualifier("wordDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/records")
    public ModelAndView putProperty(@RequestBody OneValueRecord record) {
        ModelAndView view = new ModelAndView("redirect:" + controllerPath);
        view.addObject("response", dictionary.put(record.getKey(), record.getValue()));

        return view;
    }

    @Override
    @GetMapping
    public String getDictionary(Model model) {
        model.addAttribute("dictionary", dictionary.getDictionary());
        model.addAttribute("nameDictionary", dictionary.getName());

        return "dictionary";
    }

    @Override
    @GetMapping("/values/{key}")
    public String getByKey(@PathVariable String key, Model model) {
        model.addAttribute("dictionary", dictionary.get(key));
        model.addAttribute("nameDictionary", dictionary.getName());

        return "dictionary";
    }

    @Override
    @GetMapping("/keys/{value}")
    public String getByValue(@PathVariable String value, Model model) {
        model.addAttribute("dictionary", dictionary.getKeys(value));
        model.addAttribute("nameDictionary", dictionary.getName());

        return "dictionary";
    }

    @Override
    @PutMapping("/records")
    public ModelAndView updateProperty(@RequestBody RecordForUpdate record) {
        ModelAndView view = new ModelAndView("redirect:" + controllerPath);
        view.addObject("response",
                dictionary.update(record.getKey(), record.getOldValue(), record.getNewValue()));

        return view;
    }

    @Override
    @DeleteMapping("/records/keys")
    @ResponseBody
    public ModelAndView removeKey(@RequestBody PropertyKey key) {
        ModelAndView view = new ModelAndView("redirect:" + controllerPath);
        view.addObject("response", dictionary.removeAll(key.getKey()));

        return view;
    }

    @Override
    @DeleteMapping("/records")
    public ModelAndView removeProperty(@RequestBody OneValueRecord record) {
        ModelAndView view = new ModelAndView("redirect:" + controllerPath);
        view.addObject("response", dictionary.remove(record.getKey(), record.getValue()));

        return view;
    }
}
