package com.example.model;

import java.util.Map;

public class ValidChecker implements Checker {
    private Map<String, ?> dictionary;
    private int lengthKey;
    private String regexpKey;
    private String result = "Empty result";

    public ValidChecker(Map<String, ?> dictionary, int keyLength, String keySymbols) {
        this.dictionary = dictionary;
        this.lengthKey = keyLength;
        this.regexpKey = keySymbols + "{" + keyLength + "}";
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String resultForRemove(String key, String value) {
        return result = key + "=" + value + " removed";
    }

    @Override
    public String resultForGet(String key, String value) {
        return result = key + "=" + value;
    }

    @Override
    public String resultForPut(String key, String value) {
        return result = key + "=" + value + " put";
    }

    @Override
    public boolean isValidKey(String key) {
        if (!isInvalidKey(key)) return true;

        result = key.length() < lengthKey ? "Key is too short!"
                : key.length() > lengthKey ? "Key is too long!"
                : "Key does not match the restrictions!";

        return false;
    }

    @Override
    public boolean keyContains(String key) {
        if (dictionary.containsKey(key)) return true;

        result = "Key not contains!";

        return false;
    }

    private boolean isInvalidKey(String key) {
        return !key.matches(regexpKey);
    }
}
