package com.example.validation;

public enum DictionaryError {
    Empty("DictionaryError", "Empty result!"),
    EmptyKey("Invalid key", "Empty key!"),
    EmptyValue("Invalid value", "Empty value!"),
    KeyTooLong("Invalid key", "Key too long!"),
    KeyTooShort("Invalid key", "Key too short!"),
    KeyNotMatch("Invalid key", "Key not match!"),
    RecordNotFound("Record error", "Record not found!");

    private String error;
    private String message;

    DictionaryError(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
