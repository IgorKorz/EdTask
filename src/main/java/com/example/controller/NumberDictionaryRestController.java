package com.example.controller;

import com.example.dao.Dictionary;
import com.example.entity.PropertyKey;
import com.example.controller.utility.OneValueRecord;
import com.example.model.Property;

import com.example.controller.utility.RecordForUpdate;
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
    public Property putProperty(@RequestBody OneValueRecord record) {
        return dictionary.put(record.getKey(), record.getValue());
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
    public Property updateProperty(@RequestBody RecordForUpdate record) {
        return dictionary.update(record.getKey(), record.getOldValue(), record.getNewValue());
    }

    @Override
    public Property removeKey(@RequestBody PropertyKey key) {
        return dictionary.removeAll(key.getKey());
    }

    @Override
    @DeleteMapping("/records/keys")
    public Property removeProperty(@RequestBody OneValueRecord record) {
        return dictionary.remove(record.getKey(), record.getValue());
    }
}
