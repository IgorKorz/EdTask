package com.example.model;

public interface Checker {
    Property getResult();

    Property result(String key, String value);

    boolean isValidKey(String key);

    boolean containsKey(String key);

    boolean containsValue(String value);

    boolean contains(String key, String value);
}