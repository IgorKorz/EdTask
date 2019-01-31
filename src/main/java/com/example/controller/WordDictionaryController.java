package com.example.controller;

import com.example.controller.utility.*;
import com.example.dao.Dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/word-dictionary")
public class WordDictionaryController implements DictionaryController {
    private final Dictionary dictionary;

    @Autowired
    public WordDictionaryController(@Qualifier("wordDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/records")
    public String putProperty(@RequestBody OneValueRecord record, Model model) {
        model.addAttribute("response", dictionary.put(record.getKey(), record.getValue()));

        return "dictionary";
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
        model.addAttribute("toDictionary", "/word-dictionary");

        return "search";
    }

    @Override
    @GetMapping("/keys/{value}")
    public String getByValue(@PathVariable String value, Model model) {
        model.addAttribute("dictionary", dictionary.getKeys(value));
        model.addAttribute("nameDictionary", dictionary.getName());
        model.addAttribute("toDictionary", "/word-dictionary");

        return "search";
    }

    @Override
    @PutMapping("/records")
    public String updateProperty(@RequestBody RecordForUpdate record, Model model) {
        model.addAttribute(
                "response",
                dictionary.update(record.getKey(), record.getOldValue(), record.getNewValue()));
        return "dictionary";
    }

    @Override
    @DeleteMapping("/records/keys")
    public String removeKey(@RequestBody Key key) {
        dictionary.remove(key.getKey());

        return "dictionary";
    }

    @Override
    @DeleteMapping("/records")
    public String removeProperty(@RequestBody OneValueRecord record) {
        dictionary.removeRecord(record.getKey(), record.getValue());

        return "dictionary";
    }
}
