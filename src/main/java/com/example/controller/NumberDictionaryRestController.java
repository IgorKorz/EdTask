package com.example.controller;

import com.example.dao.Dictionary;
import com.example.model.DictionaryRecord;
import com.example.model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NumberDictionaryRestController implements DictionaryRestController {
    private final Dictionary dictionary;

    @Autowired
    public NumberDictionaryRestController(@Qualifier("numberDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/rest/number-dictionary/records")
    public Property putProperty(@RequestBody DictionaryRecord property) {
        return dictionary.put(property.getKey(), property.getValues().get(0));
    }

    @Override
    @GetMapping("/rest/number-dictionary")
    public List<Property> getDictionary() {
        return dictionary.getDictionary();
    }

    @Override
    @GetMapping("/rest/number-dictionary/values/{key}")
    public List<Property> getByKey(@PathVariable String key) {
        return dictionary.get(key);
    }

    @Override
    @GetMapping("/rest/number-dictionary/keys/{value}")
    public List<Property> getByValue(@PathVariable String value) {
        return dictionary.getKeys(value);
    }

    @Override
    @PutMapping("/rest/number-dictionary/records/{key}/{oldValue}/{newValue}")
    public Property updateProperty(@PathVariable String key,
                                   @PathVariable String oldValue,
                                   @PathVariable String newValue) {
        return dictionary.update(key, oldValue, newValue);
    }

    @Override
    @DeleteMapping("/rest/number-dictionary/records/{key}")
    @ResponseBody
    public Property removeKey(@PathVariable String key) {
        return dictionary.removeAll(key);
    }

    @Override
    @DeleteMapping("/rest/number-dictionary/records/{key}/{value}")
    public Property removeProperty(@PathVariable String key,
                                   @PathVariable String value) {
        return dictionary.remove(key, value);
    }
}
