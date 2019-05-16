package com.example.model;

public enum  DictionaryType {
    LETTERS(0),
    NUMBERS(1),
    ERROR(-1);

    private int type;

    DictionaryType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
