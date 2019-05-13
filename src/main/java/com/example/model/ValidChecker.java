package com.example.model;

import java.util.List;

public class ValidChecker implements Checker {
    private List<Property> dictionary;
    private int keyLength;
    private String keyRegex;
    private Property result;

    public ValidChecker(List<Property> dictionary, int keyLength, String keyRegex) {
        this.dictionary = dictionary;
        this.keyLength = keyLength;
        this.keyRegex = keyRegex;
        this.result = new ErrorProperty(Error.Empty);
    }

    @Override
    public Property getResult() {
        return result;
    }

    @Override
    public boolean isValidKey(String key) {
        if (isEmpty(key)) {
            result = new ErrorProperty(Error.EmptyKey);

            return false;
        }

        if (key.length() > keyLength) {
            result = new ErrorProperty(Error.KeyTooLong);
            result.setValue(result.getValue() + " (length = " + keyLength + ")");

            return false;
        } else if (key.length() < keyLength) {
            result = new ErrorProperty(Error.KeyTooShort);
            result.setValue(result.getValue() + " (length = " + keyLength + ")");

            return false;
        }

        if (!key.matches(keyRegex)) {
            result = new ErrorProperty(Error.KeyNotMatch);
            result.setValue(result.getValue() + " (pattern = " + keyRegex + ")");

            return false;
        }

        return true;
    }

    @Override
    public boolean isValidValue(String value) {
        if (isEmpty(value)) {
            result = new ErrorProperty(Error.EmptyValue);

            return false;
        }

        return true;
    }

    @Override
    public int containsKey(String key) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getKey().equals(key)) {
                return i;
            }
        }

        result = new ErrorProperty(Error.RecordNotFound);

        return -1;
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
