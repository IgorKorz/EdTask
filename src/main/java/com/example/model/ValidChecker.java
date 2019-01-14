package com.example.model;

import com.example.controller.Dictionary;

import org.springframework.jdbc.core.JdbcTemplate;

public class ValidChecker implements Checker<JdbcTemplate> {
    private final String keyNotContainsMsg = "Key not contains!";
    private final String keyIsTooShortMsg = "Key is too short!";
    private final String keyIsTooLongMsg = "Key is too long!";
    private final String keyNotMatchMsg = "Key does not match the restrictions!";
    private String result;
    private Dictionary dictionary;

    public ValidChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void resultForRemove(String key) {

    }

    @Override
    public void resultForGet(String key) {

    }

    @Override
    public void resultForPut(String key) {

    }

    @Override
    public boolean isValidKey(String key) {
        if (key.length() < dictionary.getKeyLength()) {
            result = keyIsTooShortMsg;

            return false;
        }

        if (key.length() > dictionary.getKeyLength()) {
            result = keyIsTooLongMsg;

            return false;
        }

        if (!key.matches(dictionary.getKeyRegex())) {
            result = keyNotMatchMsg;

            return false;
        }

        return true;
    }

    @Override
    public boolean keyContains(String key) {
        if (!dictionary.getDictionary().containsKey(key)) {
            result = keyNotContainsMsg;

            return false;
        }

        return true;
    }
}
