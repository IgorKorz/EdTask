package com.example.controller;

import com.example.controller.utility.*;
import com.example.dao.Dictionary;
import com.example.model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public String putProperty(@RequestBody OneValueRecord record) {
        Property property = dictionary.put(record.getKey(), record.getValue());

        return property.getId() == 201
                ? "Record " + record.getKey() + "=" + record.getValue() + " put"
                : property.getId() == 200
                ? "Record " + record.getKey() + "=" + record.getValue() + " already exists"
                : property.getValues().get(0);
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
        model.addAttribute("toDictionary", "/number-dictionary");

        return "search";
    }

    @Override
    @GetMapping("/keys/{value}")
    public String getByValue(@PathVariable String value, Model model) {
        model.addAttribute("dictionary", dictionary.getKeys(value));
        model.addAttribute("nameDictionary", dictionary.getName());
        model.addAttribute("toDictionary", "/number-dictionary");

        return "search";
    }

    @Override
    @PutMapping("/records")
    @ResponseBody
    public String updateProperty(@RequestBody RecordForUpdate record) {
        Property property = dictionary.update(record.getKey(), record.getOldValue(), record.getNewValue());

        return property.getId() == 200
                ? "Record " + record.getKey() + "=" + record.getOldValue() + " now "
                            + record.getKey() + "=" + record.getNewValue()
                : property.getValues().get(0);
    }

    @Override
    @DeleteMapping("/records/keys")
    @ResponseBody
    public String removeKey(@RequestBody Key key) {
        Property property = dictionary.remove(key.getKey());

        return dictionary.remove(key.getKey()).getId() == 200
                ? "Key " + key.getKey() + " removed"
                : property.getValues().get(0);
    }

    @Override
    @DeleteMapping("/records")
    @ResponseBody
    public String removeProperty(@RequestBody OneValueRecord record) {
        Property property = dictionary.removeRecord(record.getKey(), record.getValue());

        return property.getId() == 200
                ? "Record " + record.getKey() + "=" + record.getValue() + " removed"
                : property.getValues().get(0);
    }
}
