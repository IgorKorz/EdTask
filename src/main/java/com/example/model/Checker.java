package com.example.model;

public interface Checker {
    String getResult();

    String resultForRemove(String key, String value);

    String resultForGet(String key, String value);

    String resultForPut(String key, String value);

    boolean isValidKey(String key);

    boolean keyContains(String key);
}