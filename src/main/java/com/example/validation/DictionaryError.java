package com.example.validation;

import com.example.Strings;
import com.example.model.Property;

public enum DictionaryError {
    Empty(new ErrorProperty(Strings.dictionaryError, Strings.emptyResult)),
    EmptyKey(new ErrorProperty(Strings.invalidKey, Strings.emptyKey)),
    EmptyValue(new ErrorProperty(Strings.invalidValue, Strings.emptyValue)),
    KeyTooLong(new ErrorProperty(Strings.invalidKey, Strings.keyTooLong)),
    KeyTooShort(new ErrorProperty(Strings.invalidKey, Strings.keyTooShort)),
    KeyNotFound(new ErrorProperty(Strings.keyError, Strings.keyNotFound)),
    KeyNotMatch(new ErrorProperty(Strings.keyError, Strings.keyNotMatch)),
    RecordNotFound(new ErrorProperty(Strings.recordError, Strings.recordNotFound));

    Property error;

    DictionaryError(Property error) {
        this.error = error;
    }

    public Property getError() {
        return error;
    }
}
