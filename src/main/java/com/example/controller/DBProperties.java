package com.example.controller;

import com.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;

public class DBProperties implements Dictionary {
    private SessionFactory dataSource;
    private String entityName;
    private Checker checker;
    private String name;
    private Map<String, String> dictionary;

    public DBProperties(
            SessionFactory dataSource, int keyLength, String keySymbols, String name, Class<? extends Property> entity) {
        this.dataSource = dataSource;
        this.entityName = entity.getSimpleName();
        this.name = name;
        initDictionary();
        this.checker = new ValidChecker(dictionary, keyLength, keySymbols);
    }

    //region override
    @Override
    public synchronized Map<String, String> getDictionary() {
        return dictionary;
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public String get(String key) {
        if (checker.keyContains(key)) return dictionary.get(key);
        else return checker.getResult();
    }

    @Override
    public String put(String key, String value) {
        if (checker.isValidKey(key)) {
            Session session = dataSource.openSession();
            session.beginTransaction();

            Property property;

            if (checker.keyContains(key)) {
                property = (Property) session
                        .createQuery("from " + entityName + " where key = :key")
                        .setParameter("key", key)
                        .uniqueResult();
                property.setValue(value);

                session.update(entityName, property);
            } else {
                property = new Property() {
                    private String key, value;

                    @Override
                    public long getId() {
                        return 0;
                    }

                    @Override
                    public void setId(long id) {

                    }

                    @Override
                    public String getKey() {
                        return key;
                    }

                    @Override
                    public void setKey(String key) {
                        this.key = key;
                    }

                    @Override
                    public String getValue() {
                        return value;
                    }

                    @Override
                    public void setValue(String value) {
                        this.value = value;
                    }
                };

                property.setKey(key);
                property.setValue(value);

                session.save(entityName, property);
            }

            session.getTransaction().commit();
            session.close();

            dictionary.put(key, value);

            return checker.resultForPut(key, value);
        } else return checker.getResult();
    }

    @Override
    public String remove(String key) {
        if (checker.keyContains(key)) {
            Session session = dataSource.openSession();
            session.beginTransaction();

            Property property = (Property) session
                    .createQuery("from " + entityName + " where property_key = :key")
                    .setParameter("key", key)
                    .uniqueResult();

            session.remove(property);
            session.getTransaction().commit();
            session.close();

            return checker.resultForRemove(key, dictionary.remove(key));
        } else return checker.getResult();
    }
    //endregion

    private Session openSession() {
        return dataSource.openSession();
    }

    private void initDictionary() {
        dictionary = Collections.synchronizedMap(new LinkedHashMap<>());
        List<Property> properties = openSession().createQuery("from " + entityName).list();
        openSession().close();

        for (Property property : properties) {
            String key = property.getKey();
            String value = property.getValue();
            dictionary.put(key, value);
        }
    }
}
