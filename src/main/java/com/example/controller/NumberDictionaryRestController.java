package com.example.controller;

import com.example.dao.Dictionary;
import com.example.model.DictionaryRecord;
import com.example.model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/number-dictionary")
public class NumberDictionaryRestController implements DictionaryRestController {
    private final Dictionary dictionary;

    @Autowired
    public NumberDictionaryRestController(@Qualifier("numberDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    @PostMapping("/records")
    public Property putProperty(@RequestBody DictionaryRecord property) {
        return dictionary.put(property.getKey(), property.getValues().get(0));
    }

    @Override
    @GetMapping
    public List<Property> getDictionary() {
        return dictionary.getDictionary();
    }

    @Override
    @GetMapping("/values/{key}")
    public List<Property> getByKey(@PathVariable String key) {
        return dictionary.get(key);
    }

    @Override
    @GetMapping("/keys/{value}")
    public List<Property> getByValue(@PathVariable String value) {
        return dictionary.getKeys(value);
    }

    @Override
    @PutMapping("/records")
    public Property updateProperty(@RequestBody DictionaryRecord property) {
        return dictionary.update(property.getKey(), property.getValues().get(0), property.getValues().get(1));
    }

    @Override
    @DeleteMapping("/records")
    public Property removeProperty(@RequestBody DictionaryRecord property) {
        if (property.getValues() == null || property.getValues().isEmpty())
            return dictionary.removeAll(property.getKey());
        else
            return dictionary.remove(property.getKey(), property.getValues().get(0));
    }
}
