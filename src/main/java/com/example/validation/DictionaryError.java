package com.example.validation;

import com.example.model.Property;

public enum DictionaryError {
    Empty(new ErrorProperty(ErrorStrings.DICTIONARY_ERROR, ErrorStrings.EMPTY_RESULT)),
    EmptyKey(new ErrorProperty(ErrorStrings.INVALID_KEY, ErrorStrings.EMPTY_KEY)),
    EmptyValue(new ErrorProperty(ErrorStrings.INVALID_VALUE, ErrorStrings.EMPTY_VALUE)),
    KeyTooLong(new ErrorProperty(ErrorStrings.INVALID_KEY, ErrorStrings.KEY_TOO_LONG)),
    KeyTooShort(new ErrorProperty(ErrorStrings.INVALID_KEY, ErrorStrings.KEY_TOO_SHORT)),
    KeyNotFound(new ErrorProperty(ErrorStrings.KEY_ERROR, ErrorStrings.KEY_NOT_FOUND)),
    KeyNotMatch(new ErrorProperty(ErrorStrings.KEY_ERROR, ErrorStrings.KEY_NOT_MATCH)),
    RecordNotFound(new ErrorProperty(ErrorStrings.RECORD_ERROR, ErrorStrings.RECORD_NOT_FOUND));

    Property error;

    DictionaryError(Property error) {
        this.error = error;
    }

    public Property getError() {
        return error;
    }
}
