package com.example.model;

public interface Result {
    String getResult();
    void resultForRemove(String key, String value);
    void resultForGet(String key, String value);
    void resultForPut(String key, String value);
}
