package com.example.controller;

import com.example.model.NumberProperty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class NumberDBProperties extends DBProperties {
    public NumberDBProperties(SessionFactory dataSource, int keyLength, String keyRegex, String name) {
        super(dataSource, keyLength, keyRegex, name, NumberProperty.class.getSimpleName());
    }

    @Override
    public String remove(String key) {
        if (checker.keyContains(key)) {
            String value = dictionary.remove(key);

            Session session = dataSource.openSession();
            session.beginTransaction();

            NumberProperty property = (NumberProperty) session
                    .createQuery("from " + entityName + " where property_key = :key")
                    .setParameter("key", key)
                    .uniqueResult();

            session.delete(property);
            session.getTransaction().commit();
            session.close();

            return checker.resultForRemove(key, value);
        } else return checker.getResult();
    }

    @Override
    public String get(String key) {
        if (checker.keyContains(key)) {
            String value = dictionary.get(key);

            return checker.resultForGet(key, value);
        } else return checker.getResult();
    }

    @Override
    public String put(String key, String value) {
        if (checker.isValidKey(key)) {
            Session session = dataSource.openSession();
            session.beginTransaction();

            NumberProperty property;

            if (checker.keyContains(key)) {
                property = (NumberProperty) session
                        .createQuery("from " + entityName + " where property_key = :key")
                        .setParameter("key", key)
                        .uniqueResult();
                property.setValue(value);

                session.update(property);
            }
            else {
                property = new NumberProperty();
                property.setKey(key);
                property.setValue(value);

                session.save(property);
            }

            session.getTransaction().commit();
            session.close();

            dictionary.put(key, value);

            return checker.resultForPut(key, value);
        } else return checker.getResult();
    }
}
