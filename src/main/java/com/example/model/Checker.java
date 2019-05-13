package com.example.model;

public interface Checker {
    Property getResult();

    boolean isValidKey(String key);

    boolean isValidValue(String value);

    int containsKey(String key);
}