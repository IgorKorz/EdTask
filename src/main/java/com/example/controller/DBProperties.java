package com.example.controller;

import com.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;

public abstract class DBProperties implements Dictionary {
    protected String entityName;
    protected Checker checker;
    protected SessionFactory dataSource;
    protected Map<String, List<String>> dictionary;
    private int keyLength;
    private String keyRegex;
    private String name;

    public DBProperties(SessionFactory dataSource, int keyLength, String keyRegex, String name, String entityName) {
        this.entityName = entityName;
        this.checker = new ValidChecker(this);
        this.dataSource = dataSource;
        this.keyLength = keyLength;
        this.keyRegex = keyRegex + "{" + keyLength + "}";
        this.name = name;
        initDictionary();
    }

    //region override
    @Override
    public synchronized Map<String, String> getDictionary() {
        Map<String, String> keyWithValues = new LinkedHashMap<>();

        for (String key : dictionary.keySet()) {
            keyWithValues.put(key, values(key));
        }

        return keyWithValues;
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
    //endregion

    protected Session openSession() {
        return dataSource.openSession();
    }

    protected String values(String key) {
        List<String> valueList = dictionary.get(key);
        String values = "";

        for (String value : valueList) {
            values += value + ";";
        }

        return values;
    }

    private void initDictionary() {
        dictionary = Collections.synchronizedMap(new LinkedHashMap<>());
        List<Property> properties = openSession().createQuery("from " + entityName).list();
        openSession().close();

        for (Property property : properties) {
            String key = property.getKey();
            String value = property.getValue();

            if (dictionary.containsKey(property.getKey())) {
                dictionary.get(key).add(value);
            } else {
                dictionary.put(key, new LinkedList<>());
                dictionary.get(key).add(value);
            }
        }
    }
}
