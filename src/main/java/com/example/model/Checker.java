package com.example.model;

public interface Checker {
    Property getResult();

    boolean isValidKey(String key);

    boolean containsKey(String key);
}