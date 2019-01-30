package com.example.model;

import com.example.entity.Key;
import com.example.entity.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

public class DictionaryRecord implements Property {
    @JsonIgnore
    private long id;

    private String key;
    private List<String> values;

    public DictionaryRecord(Key key) {
        this.key = key.getKey();
        this.values = new LinkedList<>();

        for (Value value : key.getValues()) {
            values.add(value.getValue());
        }
    }

    public DictionaryRecord() {

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    @Override
    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setValues(String value) {
        values = new LinkedList<>();
        values.add(value);
    }

    @Override
    public void addValue(String value) {
        values.add(value);
    }
}
