package com.example.validation;

import com.example.model.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DictionaryValidator implements Validator {
    private JdbcTemplate jdbcTemplate;
    private DictionaryError result;
    private final DictionaryError defaultError = DictionaryError.Empty;

    public DictionaryValidator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Property getResult() {
        return result.getError();
    }

    @Override
    public boolean isValidRecord(String key, String value, int keyLength, String keyRegex) {
        return isValidKey(key, keyLength, keyRegex) && isValidValue(value);
    }

    @Override
    public boolean keyExists(String key, int type) {
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

        result = defaultError;

        return true;
    }

    @Override
    public boolean recordExists(String key, String value, int type) {
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

        result = defaultError;

        return true;
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private RowMapper<Boolean> boolMap() {
        return (set, row) -> set.getBoolean(1);
    }

    private boolean isValidKey(String key, int keyLength, String keyRegex) {
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

        result = defaultError;

        return true;
    }

    private boolean isValidValue(String value) {
        if (isEmpty(value)) {
            result = DictionaryError.EmptyValue;

            return false;
        }

        result = defaultError;

        return true;
    }
}
