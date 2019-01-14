package com.example.model;

public interface Checker<T> {
    String getResult();
    void resultForRemove(String key);
    void resultForGet(String key);
    void resultForPut(String key);
    boolean isValidKey(String key);
    boolean keyContains(String key);
}
