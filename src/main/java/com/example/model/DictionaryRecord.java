package com.example.model;

public class DictionaryRecord implements Property {
    private String key;
    private String value;
    private DictionaryType type;

    public DictionaryRecord(String key, String value, DictionaryType type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }
    public DictionaryRecord() { }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public DictionaryType getType() {
        return type;
    }

    @Override
    public void setType(DictionaryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
