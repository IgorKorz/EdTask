package com.example.validation;

import com.example.model.Property;

public class ErrorProperty implements Property {
    private String error;
    private String message;

    public ErrorProperty(String error, String message) {
        this.error = error;
        this.message = message;
    }
    public ErrorProperty(DictionaryError error) {
        this.error = error.getError();
        this.message = error.getMessage();
    }

    @Override
    public long getId() {
        return -1;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public String getKey() {
        return error;
    }

    @Override
    public void setKey(String error) {
        this.error = error;
    }

    @Override
    public String getValue() {
        return message;
    }

    @Override
    public void setValue(String message) {
        this.message = message;
    }

    @Override
    public int getType() {
        return -1;
    }

    @Override
    public void setType(int type) {

    }

    @Override
    public String toString() {
        return error + ": " + message;
    }
}
