package com.example.controller;

import com.example.model.DictionaryRecord;
import com.example.model.Property;
import com.example.validation.ErrorProperty;
import com.example.validation.Validator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

public class DBDictionary implements Dictionary {
    private JdbcTemplate jdbcTemplate;
    private Validator validator;
    private int keyLength;
    private String keyRegex;
    private String name;
    private int type;


    public DBDictionary(
            JdbcTemplate jdbcTemplate,
            Validator validator,
            int keyLength,
            String keyRegex,
            String name,
            int type
            ) {
        this.jdbcTemplate = jdbcTemplate;
        this.validator = validator;
        this.keyLength = keyLength;
        this.keyRegex = keyRegex;
        this.name = name;
        this.type = type;
    }

    @Override
    public synchronized List<Property> getDictionary() {
        try {
            return jdbcTemplate.query(
                    "SELECT key, value FROM key k JOIN value v ON k.id = v.key_id WHERE type = ?",
                    recordMap(),
                    type
            );
        } catch (DataAccessException e) {
            return Collections.singletonList(new ErrorProperty(e));
        }
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized Property put(String key, String value) {
        try {
            if (!validator.isValidRecord(key, value, keyLength, keyRegex)) return validator.getResult();

            if (validator.keyExists(key, type)) {
                if (validator.recordExists(key, value, type)) {
                    jdbcTemplate.update(
                            "UPDATE value SET value = ? WHERE key_id = get_key_id(?, ?)",
                            value,
                            key,
                            type
                    );
                } else {
                    insertIntoValueTable(key, value);
                }
            } else {
                jdbcTemplate.update(
                        "INSERT INTO key (key, type) VALUES (?, ?)",
                        key,
                        type
                );

                insertIntoValueTable(key, value);
            }

            return new DictionaryRecord(key, value, type);
        } catch (DataAccessException e) {
            return new ErrorProperty(e);
        }
    }

    @Override
    public synchronized List<Property> get(String key) {
        try {
            if (!validator.keyExists(key, type)) return Collections.singletonList(validator.getResult());

            return jdbcTemplate.query(
                    "SELECT * FROM key k JOIN value v ON k.id = v.key_id WHERE key = ?",
                    recordMap(),
                    key
            );
        } catch (DataAccessException e) {
            return Collections.singletonList(new ErrorProperty(e));
        }
    }

    @Override
    public synchronized Property remove(String key, String value) {
        try {
            if (!validator.keyExists(key, type)) return validator.getResult();

            if (!validator.recordExists(key, value, type)) return validator.getResult();

            jdbcTemplate.update(
                    "DELETE FROM value WHERE key_id = get_key_id(?, ?) AND value = ?",
                    key,
                    type,
                    value
            );

            return new DictionaryRecord(key, value, type);
        } catch (DataAccessException e) {
            return new ErrorProperty(e);
        }
    }

    @Override
    public void initDictionary() {
        try {
            jdbcTemplate.execute(
                    "CREATE TABLE IF NOT EXISTS key" +
                            "(" +
                            "id BIGSERIAL NOT NULL," +
                            "key VARCHAR(255) NOT NULL," +
                            "type INTEGER NOT NULL," +
                            "CONSTRAINT unique_id PRIMARY KEY (id)," +
                            "CONSTRAINT dict_key UNIQUE (key, type)" +
                            ")"
            );
            jdbcTemplate.execute(
                    "CREATE TABLE IF NOT EXISTS value" +
                            "(" +
                            "id BIGSERIAL NOT NULL," +
                            "value VARCHAR(255) NOT NULL," +
                            "key_id BIGINT," +
                            "CONSTRAINT unique_val_id PRIMARY KEY (id)," +
                            "CONSTRAINT property UNIQUE (key_id, value)," +
                            "CONSTRAINT key_id FOREIGN KEY (key_id)" +
                            "   REFERENCES key (id) MATCH SIMPLE" +
                            "   ON UPDATE NO ACTION" +
                            "   ON DELETE CASCADE" +
                            ")"
            );
            jdbcTemplate.execute(
                    "CREATE OR REPLACE FUNCTION key_exists(k varchar, t integer) RETURNS boolean AS $$" +
                            "BEGIN " +
                            "RETURN EXISTS(SELECT 1 FROM key WHERE key = k AND type = t);" +
                            "END" +
                            "$$ LANGUAGE PLPGSQL"
            );
            jdbcTemplate.execute(
                    "CREATE OR REPLACE FUNCTION record_exists(k varchar, v varchar, t integer) RETURNS boolean AS $$" +
                            "BEGIN " +
                            "RETURN EXISTS(SELECT 1 FROM key JOIN value ON key.id = key_id WHERE key = k AND value = v AND type = t);" +
                            "END" +
                            "$$ LANGUAGE PLPGSQL"
            );
            jdbcTemplate.execute(
                    "CREATE OR REPLACE FUNCTION get_key_id(k varchar, t integer) RETURNS integer AS $$" +
                            "BEGIN " +
                            "RETURN (SELECT id FROM key WHERE key = k AND type = t);" +
                            "END" +
                            "$$ LANGUAGE PLPGSQL"
            );
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    private RowMapper<Property> recordMap() {
        return (set, row) -> {
            Property record = new DictionaryRecord();
            record.setKey(set.getString("key"));
            record.setValue(set.getString("value"));
            record.setType(type);

            return record;
        };
    }

    private void insertIntoValueTable(String key, String value) throws DataAccessException {
        jdbcTemplate.update("INSERT INTO value (value, key_id) VALUES (?, get_key_id(?, ?))", value, key, type);
    }
}
