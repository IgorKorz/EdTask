package com.example.model;

import java.util.Collections;
import java.util.List;

public class ValidChecker implements Checker {
    private List<Property> dictionary;
    private int lengthKey;
    private String regexpKey;
    private Result result;

    public ValidChecker(List<Property> dictionary, int keyLength, String keySymbols) {
        this.dictionary = dictionary;
        this.lengthKey = keyLength;
        this.regexpKey = keySymbols + "{" + keyLength + "}";
        this.result = new Result();
    }

    @Override
    public Property getResult() {
        return result;
    }

    @Override
    public Property result(long id, String key, String value) {
        Property result = new Result();
        result.setId(id);
        result.setKey(key);
        result.addValue(value);

        return result;
    }

    @Override
    public boolean isValidKey(String key) {
        if (key == null || key.isEmpty()) {
            result.setId(400);
            result.setKey("error");
            result.addValue("Empty key!");

            return false;
        }

        if (!isInvalidKey(key)) {
            result.setId(200);
            result.setKey("success");
            result.addValue("Key " + key + " is valid");

            return true;
        }

        result.setId(400);
        result.setKey("error");
        result.addValue(key.length() != lengthKey
                        ? "Key's length must be " + lengthKey + " symbols!"
                        : "Key does not match the restrictions!");

        return false;
    }

    @Override
    public boolean isValidValue(String value) {
        if (value == null || value.isEmpty()) {
            result.setId(400);
            result.setKey("error");
            result.addValue("Empty value!");

            return false;
        }

        return true;
    }

    @Override
    public boolean containsKey(String key) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getKey().equals(key)) {
                result.setId(i);
                result.setKey("Search success");
                result.addValue("Property with key " + key + " exists!");

                return true;
            }
        }

        result.setId(404);
        result.setKey("Search fail");
        result.addValue("Key " + key + " not contains!");

        return false;
    }

    @Override
    public boolean containsValue(String value) {
        for (int i = 0; i < dictionary.size(); i++) {
            for (String v : dictionary.get(i).getValues())
                if (v.equals(value)) {
                    result.setId(i);
                    result.setKey("Search success");
                    result.addValue("Property with value " + value + " exists!");

                    return true;
                }
        }

        result.setId(404);
        result.setKey("Search fail");
        result.addValue("Property with value " + value + " not contains!");

        return false;
    }

    @Override
    public boolean contains(String key, String value) {
        if (containsKey(key)) {
            for (int i = (int) result.getId(); i < dictionary.size(); i++) {
                Property property = dictionary.get(i);

                if (property.getKey().equals(key))
                    for (String v : dictionary.get(i).getValues())
                        if (v.equals(value)) {
                            result.setId(i);
                            result.setKey("Search success");
                            result.addValue("Property with key " + key + " and value " + value + " exists!");

                            return true;
                        }
            }
        }

        result.setId(404);
        result.setKey("Search fail");
        result.addValue("Property with key " + key + " and value " + value + " not contains!");

        return false;
    }

    private boolean isInvalidKey(String key) {
        return !key.matches(regexpKey);
    }

    private class Result implements Property {
        private long id = -400;
        private String key;
        private String value;

        Result() {
            this.key = "error";
            this.value = "Empty result!";
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
            return Collections.singletonList(value);
        }

        @Override
        public void addValue(String value) {
            this.value = value;
        }
    }
}