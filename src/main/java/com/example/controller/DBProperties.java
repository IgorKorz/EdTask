package com.example.controller;

import com.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class DBProperties implements Dictionary {
    protected String entityName;
    protected Checker checker;
    protected SessionFactory dataSource;
    protected Map<String, String> dictionary;
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
    //endregion

    private void initDictionary() {
        dictionary = Collections.synchronizedMap(new LinkedHashMap<>());
        List<Property> properties = openSession().createQuery("from " + entityName).list();
        openSession().close();

        for (Property property : properties) {
            dictionary.put(property.getKey(), property.getValue());
        }
    }

    protected Session openSession() {
        return dataSource.openSession();
    }
}
