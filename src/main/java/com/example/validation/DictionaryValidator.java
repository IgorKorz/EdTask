package com.example.validation;

import com.example.model.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DictionaryValidator implements Validator {
    private JdbcTemplate jdbcTemplate;
    private int keyLength;
    private String keyRegex;
    private int type;
    private DictionaryError result;

    public DictionaryValidator(JdbcTemplate jdbcTemplate, int keyLength, String keyRegex, int type) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyLength = keyLength;
        this.keyRegex = keyRegex;
        this.type = type;
        this.result = DictionaryError.Empty;
    }

    @Override
    public Property getResult() {
        return result.getError();
    }

    @Override
    public boolean isValidRecord(String key, String value) {
        return isValidKey(key) && isValidValue(value);
    }

    @Override
    public boolean keyExists(String key) {
        boolean keyExists = jdbcTemplate.query(
                "SELECT key_exists(?, ?)",
                boolMap(),
                key,
                type
        ).get(0);

        if (!keyExists) {
            result = DictionaryError.KeyNotFound;

            return false;
        }

        result = DictionaryError.Empty;

        return true;
    }

    @Override
    public boolean recordExists(String key, String value) {
        boolean valueExists = jdbcTemplate.query(
                "SELECT record_exists(?, ?, ?)",
                boolMap(),
                key,
                value,
                type
        ).get(0);

        if (!valueExists) {
            result = DictionaryError.RecordNotFound;

            return false;
        }

        result = DictionaryError.Empty;

        return true;
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private RowMapper<Boolean> boolMap() {
        return (set, row) -> set.getBoolean(1);
    }

    private boolean isValidKey(String key) {
        if (isEmpty(key)) {
            result = DictionaryError.EmptyKey;

            return false;
        }

        if (key.length() > keyLength) {
            result = DictionaryError.KeyTooLong;

            return false;
        } else if (key.length() < keyLength) {
            result = DictionaryError.KeyTooShort;

            return false;
        }

        if (!key.matches(keyRegex)) {
            result = DictionaryError.KeyNotMatch;

            return false;
        }

        result = DictionaryError.Empty;

        return true;
    }

    private boolean isValidValue(String value) {
        if (isEmpty(value)) {
            result = DictionaryError.EmptyValue;

            return false;
        }

        result = DictionaryError.Empty;

        return true;
    }
}
