package com.example.controller;

import com.example.dao.Dictionary;
import com.example.model.DictionaryRecord;
import com.example.model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordDictionaryController implements DictionaryController {
    private final Dictionary dictionary;

    @Autowired
    public WordDictionaryController(@Qualifier("wordDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/word-dictionary/records")
    public Property putProperty(@RequestBody DictionaryRecord property) {
        return dictionary.put(property.getKey(), property.getValues().get(0));
    }

    @Override
    @GetMapping("/word-dictionary")
    public List<Property> getDictionary() {
        return dictionary.getDictionary();
    }

    @Override
    @GetMapping("/word-dictionary/values/{key}")
    public List<Property> getByKey(@PathVariable String key) {
        return dictionary.get(key);
    }

    @Override
    @GetMapping("/word-dictionary/keys/{value}")
    public List<Property> getByValue(@PathVariable String value) {
        return dictionary.getKeys(value);
    }

    @Override
    @PutMapping(value = "/word-dictionary/records", params = { "key", "oldValue", "newValue" })
    public Property updateProperty(@RequestParam(name = "key") String key,
                                   @RequestParam(name = "oldValue") String oldValue,
                                   @RequestParam(name = "newValue") String newValue) {
        return dictionary.update(key, oldValue, newValue);
    }

    @Override
    @DeleteMapping("/word-dictionary/records/{key}")
    @ResponseBody
    public Property removeKey(@PathVariable String key) {
        return dictionary.removeAll(key);
    }

    @Override
    @DeleteMapping("/word-dictionary/records/{key}/{value}")
    public Property removeProperty(@PathVariable String key,
                                   @PathVariable String value) {
        return dictionary.remove(key, value);
    }
}
