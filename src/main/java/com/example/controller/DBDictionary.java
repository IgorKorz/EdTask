package com.example.controller;

import com.example.model.DictionaryRecord;
import com.example.model.Property;
import com.example.validation.DictionaryValidator;
import com.example.validation.Validator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

public class DBDictionary implements Dictionary {
    private JdbcTemplate jdbcTemplate;
    private Validator validator;
    private String name;
    private int type;


    public DBDictionary(JdbcTemplate jdbcTemplate, int keyLength, String keySymbols, String name, int type) {
            this.jdbcTemplate = jdbcTemplate;
            this.name = name;
            this.type = type;

            initDictionary();

            this.validator = new DictionaryValidator(jdbcTemplate, keyLength, keySymbols, type);
    }

    @Override
    public synchronized List<Property> getDictionary() {
        return jdbcTemplate.query(
                "SELECT key, value FROM key k JOIN value v ON k.id = v.key_id WHERE type = ?",
                recordMap(),
                type
        );
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized Property put(String key, String value) {
        if (!validator.isValidRecord(key, value)) return validator.getResult();

        if (validator.keyExists(key)) {
            if (validator.recordExists(key, value)) {
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
    }

    @Override
    public synchronized List<Property> get(String key) {
        if (!validator.keyExists(key)) return Collections.singletonList(validator.getResult());

        return jdbcTemplate.query(
                "SELECT * FROM key k JOIN value v ON k.id = v.key_id WHERE key = ?",
                recordMap(),
                key
        );
    }

    @Override
    public synchronized Property remove(String key, String value) {
        if (!validator.keyExists(key)) return validator.getResult();

        if (!validator.recordExists(key, value)) return validator.getResult();

        return jdbcTemplate.query(
                "DELETE FROM value WHERE key_id = get_key_id(?, ?) AND value = ?",
                recordMap(),
                key,
                type,
                value
        ).get(0);
    }

    private void initDictionary() {
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
                        "RETURN id FROM key WHERE key = k AND type = t;" +
                        "END" +
                        "$$ LANGUAGE PLPGSQL"
        );
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

    private void insertIntoValueTable(String key, String value) {
        jdbcTemplate.update("INSERT INTO value (value, key_id) VALUES (?, get_key_id(?, ?))", value, key, type);
    }
}
