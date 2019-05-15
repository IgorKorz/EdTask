package com.example.validation;

import com.example.model.Property;

public enum DictionaryError {
    Empty(new ErrorProperty("DictionaryError", "Empty result!")),
    EmptyKey(new ErrorProperty("Invalid key", "Empty key!")),
    EmptyValue(new ErrorProperty("Invalid value", "Empty value!")),
    KeyTooLong(new ErrorProperty("Invalid key", "Key too long!")),
    KeyTooShort(new ErrorProperty("Invalid key", "Key too short!")),
    KeyNotFound(new ErrorProperty("Key error", "Key not found")),
    KeyNotMatch(new ErrorProperty("Invalid key", "Key not match!")),
    RecordNotFound(new ErrorProperty("Record error", "Record not found!"));

    Property error;

    DictionaryError(Property error) {
        this.error = error;
    }

    public Property getError() {
        return error;
    }

    private static class ErrorProperty implements Property {
        private String error;
        private String message;

        ErrorProperty(String error, String message) {
            this.error = error;
            this.message = message;
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
}
