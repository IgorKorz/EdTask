package com.example.controller;

import java.util.Map;

public interface Dictionary {
    Map<String, String> getDictionary();

    String getName();

    String remove(String key);

    String get(String key);

    String put(String key, String value);
}