package com.example.controller;

import com.example.model.*;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBProperties implements Dictionary {
    private final int keyLength;
    private final String keyRegex;
    private final String name;
    private final String tableName;
    private Source<JdbcTemplate> source;
    private Checker checker;
    private Map<String, String> dictionary;

    public DBProperties(JdbcTemplate dataSource, int keyLength, String keyRegex, String name) {
        this.keyLength = keyLength;
        this.keyRegex = keyRegex;
        this.name = name;
        this.tableName = name.toLowerCase().replace(' ', '_');
        this.source = new DBSource(dataSource, name, keyLength);
        this.checker = new ValidChecker(this);
        initDictionary();
    }

    //region override
    @Override
    public synchronized Map<String, String> getDictionary() {
        return new LinkedHashMap<>(dictionary);
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized String getKeyRegex() {
        return keyRegex;
    }

    @Override
    public synchronized int getKeyLength() {
        return keyLength;
    }

    @Override
    public synchronized String remove(String key) {
        if (checker.keyContains(key)) {
            String value = dictionary.remove(key);

            source.getSource().update("delete from " + tableName + " where property_key = ?", key);

            return checker.resultForRemove(key, value);
        } return checker.getResult();
    }

    @Override
    public synchronized String get(String key) {
        if (checker.keyContains(key)) {
            String value = dictionary.get(key);

            return checker.resultForGet(key, value);
        } else return checker.getResult();
    }

    @Override
    public synchronized String put(String key, String value) {
        if (checker.isValidKey(key)) {
            if (checker.keyContains(key)) source.getSource().update("update " + tableName +
                                                                            " set property_value = ?" +
                                                                            " where property_key = ?",
                    value, key);
            else source.getSource().update("insert into " + tableName + "(property_key, property_value) values(?, ?)",
                    key, value);

            dictionary.put(key, value);

            return checker.resultForPut(key, value);
        } else return checker.getResult();
    }

    //endregion

    private void initDictionary() {
        dictionary = Collections.synchronizedMap(new LinkedHashMap<>());

        List<Property> queryResult = source.getSource().query(
                "select * from " + tableName,
                (resultSet, i) -> new Property(resultSet.getLong("id"),
                        resultSet.getString("property_key"),
                        resultSet.getString("property_value"))
        );

        for (Property property : queryResult)
            dictionary.put(property.getKey(), property.getValue());
    }
}
