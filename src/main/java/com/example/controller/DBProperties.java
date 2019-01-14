package com.example.controller;

import com.example.model.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

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

    public DBProperties(JdbcTemplate dataSource, int keyLength, String keyRegex, String name) {
        this.keyLength = keyLength;
        this.keyRegex = keyRegex;
        this.name = name;
        this.source = new DBSource(dataSource, name, keyLength);
        this.checker = new ValidChecker(this);
        this.tableName = name.toLowerCase().replace(' ', '_');
    }

    //region override
    @Override
    public Map<String, String> getDictionary() {
        Map<String, String> dictionary = new LinkedHashMap<>();System.out.println("Filling dictionary");

        List<Property> queryResult = source.getSource().query(
                "select * from ?", new Object[]{ tableName },
                (resultSet, i) -> new Property(resultSet.getLong("id"),
                        resultSet.getString("property_key"),
                        resultSet.getString("property_value"))
        );

        for (Property property : queryResult)
            dictionary.put(property.getKey(), property.getValue());

        return dictionary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getKeyRegex() {
        return keyRegex;
    }

    @Override
    public int getKeyLength() {
        return keyLength;
    }

    @Override
    public String remove(String key) {
        return null;
    }

    @Override
    public String get(String key) {
//        Property property = source.getSource().query(
//                "select * from ? where property_key = ?", new Object[]{tableName, key},
//                (ResultSetExtractor<Property>) resultSet -> new Property(resultSet.getLong("id"),
//                                            resultSet.getString("property_key"),
//                                            resultSet.getString("property_value"))
//        );

        return null;
    }

    @Override
    public String put(String key, String value) {
        return null;
    }

    @Override
    public void write() {

    }
    //endregion
}
