package com.example.controller;

import com.example.dao.Dictionary;
import com.example.model.DictionaryRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WordDictionaryController implements DictionaryController {
    private final Dictionary dictionary;

    @Autowired
    public WordDictionaryController(@Qualifier("wordDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/word-dictionary/records")
    public String putProperty(@RequestBody DictionaryRecord property, Model model) {
        model.addAttribute("response", dictionary.put(property.getKey(), property.getValues().get(0)));

        return "dictionary";
    }

    @Override
    @GetMapping("/word-dictionary")
    public String getDictionary(Model model) {
        model.addAttribute("dictionary", dictionary.getDictionary());

        return "dictionary";
    }

    @Override
    @GetMapping("/word-dictionary/values/{key}")
    public String getByKey(@PathVariable String key, Model model) {
        model.addAttribute("dictionary", dictionary.get(key));

        return "dictionary";
    }

    @Override
    @GetMapping("/word-dictionary/keys/{value}")
    public String getByValue(@PathVariable String value, Model model) {
        model.addAttribute("dictionary", dictionary.getKeys(value));

        return "dictionary";
    }

    @Override
    @PutMapping(value = "/word-dictionary/records", params = { "key", "oldValue", "newValue" })
    public String updateProperty(@RequestParam(name = "key") String key,
                                 @RequestParam(name = "oldValue") String oldValue,
                                 @RequestParam(name = "newValue") String newValue,
                                 Model model) {
        model.addAttribute("response", dictionary.update(key, oldValue, newValue));

        return "dictionary";
    }

    @Override
    @DeleteMapping("/word-dictionary/records/{key}")
    @ResponseBody
    public String removeKey(@PathVariable String key, Model model) {
        model.addAttribute("response", dictionary.removeAll(key));

        return "dictionary";
    }

    @Override
    @DeleteMapping("/word-dictionary/records/{key}/{value}")
    public String removeProperty(@PathVariable String key,
                                 @PathVariable String value,
                                 Model model) {
        model.addAttribute("response", dictionary.remove(key, value));

        return "dictionary";
    }
}
