package com.example.dao;

import com.example.entity.*;
import com.example.model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Repository
public class DBProperties implements Dictionary {
    private SessionFactory sessionFactory;
    private Checker checker;
    private String name;
    private int type;
    private List<Property> dictionary;
    private String keyEntity = PropertyKey.class.getSimpleName();
    private String valEntity = PropertyValue.class.getSimpleName();

    public DBProperties(SessionFactory sessionFactory, int keyLength, String keySymbols, String name, int type) {
        this.sessionFactory = sessionFactory;
        this.name = name;
        this.type = type;
        initDictionary();
        this.checker = new ValidChecker(dictionary, keyLength, keySymbols);
    }

    public DBProperties() {

    }

    @Override
    public synchronized Property put(String key, String value) {
        if (!checker.isValidKey(key)) return checker.getResult();

        if (!checker.isValidValue(value)) return checker.getResult();

        if (checker.contains(key, value)) return checker.result(200, key, value);

        Session session = sessionFactory.getCurrentSession();
        Key propertyKey;

        if (checker.containsKey(key)) {
            propertyKey = session
                    .createQuery("from " + keyEntity
                            + " where key = :key and type = : type", Key.class)
                    .setParameter("key", key)
                    .setParameter("type", type)
                    .uniqueResult();
        } else {
            propertyKey = new PropertyKey(key, type);

            dictionary.add(new DictionaryRecord(propertyKey));

            session.save(propertyKey);
        }

        Value propertyValue = new PropertyValue(propertyKey, value);

        session.save(propertyValue);

        for (Property property : dictionary)
            if (property.getKey().equals(key)) {
                property.addValue(value);

                break;
            }

        return checker.result(201, key, value);
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized List<Property> getDictionary() {
        Collections.sort(dictionary);

        for (Property property : dictionary)
            Collections.sort(property.getValues());

        return new ArrayList<>(dictionary);
    }

    @Override
    public synchronized List<Property> get(String key) {
        if (!checker.containsKey(key)) return Collections.singletonList(checker.getResult());

        List<Property> propertyList = new ArrayList<>();

        for (int i = (int) checker.getResult().getId(); i < dictionary.size(); i++) {
            Property property = dictionary.get(i);

            if (dictionary.get(i).getKey().equals(key))
                propertyList.add(property);
        }

        Collections.sort(propertyList);

        for (Property property : propertyList)
            Collections.sort(property.getValues());

        return propertyList;
    }

    @Override
    public synchronized List<Property> getKeys(String value) {
        if (!checker.containsValue(value)) return Collections.singletonList(checker.getResult());

        List<Property> propertyList = new ArrayList<>();

        for (int i = (int) checker.getResult().getId(); i < dictionary.size(); i++) {
            Property property = dictionary.get(i);

            for (String v : property.getValues())
                if (v.equals(value))
                    propertyList.add(property);
        }

        Collections.sort(propertyList);

        for (Property property : propertyList)
            Collections.sort(property.getValues());

        return propertyList;
    }

    @Override
    public synchronized Property update(String key, String oldValue, String newValue) {
        if (!checker.isValidKey(key) || !checker.isValidValue(oldValue) || !checker.isValidValue(newValue))
            return checker.getResult();

        if (!checker.contains(key, oldValue)) return checker.getResult();

        if (checker.contains(key, newValue)) {
            checker.getResult().setId(400);

            return checker.getResult();
        }

        Session session = sessionFactory.getCurrentSession();
        Value propertyValue = session
                .createQuery("from " + valEntity
                        + " where key.key = :key and key.type = :type and value = :value", Value.class)
                .setParameter("key", key)
                .setParameter("type", type)
                .setParameter("value", oldValue)
                .getSingleResult();
        propertyValue.setValue(newValue);

        session.update(propertyValue);

        for (Property property : dictionary) {
            if (property.getKey().equals(key)) {
                for (int j = 0; j < property.getValues().size(); j++)
                    if (property.getValues().get(j).equals(oldValue)) {
                        property.getValues().set(j, newValue);

                        break;
                    }

                break;
            }
        }

        return checker.result(200, key, newValue);
    }

    @Override
    public synchronized Property remove(String key) {
        if (!checker.containsKey(key)) return checker.getResult();

        Session session = sessionFactory.getCurrentSession();
        Key propertyKey = session
                .createQuery("from " + keyEntity
                        + " where key = :key and type = :type", Key.class)
                .setParameter("key", key)
                .setParameter("type", type)
                .uniqueResult();

        session.remove(propertyKey);

        for (int i = (int) checker.getResult().getId(); i < dictionary.size(); i++) {
            if (dictionary.get(i).getKey().equals(key))
                dictionary.remove(i);
        }

        return checker.result(200, key, "");
    }

    @Override
    public synchronized Property removeRecord(String key, String value) {
        if (!checker.contains(key, value)) return checker.getResult();

        Session session = sessionFactory.getCurrentSession();
        List<Value> valueList = session
                .createQuery("from " + valEntity
                        +" where key.key = :key and key.type = :type and value = :value", Value.class)
                .setParameter("key", key)
                .setParameter("type", type)
                .setParameter("value", value)
                .getResultList();

        for (Value v : valueList)
            session.remove(v);

        for (int i = (int) checker.getResult().getId(); i < dictionary.size(); i++) {
            Property property = dictionary.get(i);

            if (property.getKey().equals(key)) {
                for (int j = 0; j < property.getValues().size(); j++)
                    if (property.getValues().get(j).equals(value))
                        property.getValues().remove(j);

                break;
            }
        }

        return checker.result(200, key, value);
    }

    public synchronized void initDictionary() {
        Session session = sessionFactory.openSession();

        List<Key> keyList = session
                .createQuery("from " + keyEntity
                        + " where type = :type", Key.class)
                .setParameter("type", type)
                .getResultList();

        dictionary = Collections.synchronizedList(new ArrayList<>());

        for (Key key : keyList) {
            dictionary.add(new DictionaryRecord(key));
        }
    }
}
