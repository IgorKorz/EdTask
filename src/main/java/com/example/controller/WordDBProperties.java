package com.example.controller;

import com.example.model.WordProperty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.LinkedList;
import java.util.List;

public class WordDBProperties extends DBProperties {
    public WordDBProperties(SessionFactory dataSource, int keyLength, String keyRegex, String name) {
        super(dataSource, keyLength, keyRegex, name, WordProperty.class.getSimpleName());
    }

    @Override
    public String remove(String key) {
        if (checker.keyContains(key)) {
            String value = values(key);
            dictionary.remove(key);

            Session session = dataSource.openSession();
            session.beginTransaction();

            List<WordProperty> properties = session
                    .createQuery("from " + entityName + " where property_key = :key")
                    .setParameter("key", key)
                    .list();

            for (WordProperty property : properties) {
                session.remove(property);
            }

            session.getTransaction().commit();
            session.close();

            return checker.resultForRemove(key, value);
        } else return checker.getResult();
    }

    @Override
    public String get(String key) {
        if (checker.keyContains(key)) {
            return checker.resultForGet(key, values(key));
        } else return checker.getResult();
    }

    @Override
    public String put(String key, String value) {
        if (checker.isValidKey(key)) {
            WordProperty property = new WordProperty();
            property.setKey(key);
            property.setValue(value);

            Session session = dataSource.openSession();
            session.beginTransaction();
            session.save(property);
            session.getTransaction().commit();
            session.close();

            if (checker.keyContains(key)) {
                dictionary.get(key).add(value);
            } else {
                dictionary.put(key, new LinkedList<>());
                dictionary.get(key).add(value);
            }

            return checker.resultForPut(key, value);
        } else return checker.getResult();
    }
}
