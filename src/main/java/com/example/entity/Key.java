package com.example.entity;

import java.util.List;

public interface Key {
    long getId();

    void setId(long id);

    String getKey();

    void setKey(String key);

    List<Value> getValues();

    int getType();

    void setType(int type);
}
