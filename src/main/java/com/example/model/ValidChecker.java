package com.example.model;

import java.util.List;

public class ValidChecker implements Checker {
    private List<Property> dictionary;
    private int lengthKey;
    private String regexpKey;
    private Property result;

    public ValidChecker(List<Property> dictionary, int keyLength, String keySymbols) {
        this.dictionary = dictionary;
        this.lengthKey = keyLength;
        this.regexpKey = keySymbols + "{" + keyLength + "}";
        this.result = new DictionaryRecord();

        result.setId(-1);
        result.setKey("fail");
        result.setValue("Empty result!");
    }

    @Override
    public Property getResult() {
        return result;
    }

    @Override
    public boolean isValidKey(String key) {
        if (isEmpty(key)) {
            result.setId(-1);
            result.setKey("fail");
            result.setValue("Empty key!");

            return false;
        }

        if (!isInvalidKey(key)) {
            result.setKey("success");

            return true;
        }

        result.setId(-1);
        result.setKey("fail");
        result.setValue(key.length() != lengthKey
                ? "Key's length must be " + lengthKey + " symbols!"
                : "Key does not match the restrictions!");

        return false;
    }

    @Override
    public boolean isValidValue(String value) {
        if (isEmpty(value)) {
            result.setId(-1);
            result.setKey("fail");
            result.setValue("Empty value!");

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
                result.setValue("Property with key " + key + " find on " + i + " position");

                return true;
            }
        }

        result.setId(-1);
        result.setKey("Search fail");
        result.setValue("Key not contains!");

        return false;
    }

    private boolean isInvalidKey(String key) {
        return !key.matches(regexpKey);
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
