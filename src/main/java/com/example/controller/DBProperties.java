package com.example.controller;

import com.example.model.Checker;
import com.example.model.DictionaryRecord;
import com.example.model.Property;
import com.example.model.ValidChecker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
public class DBProperties implements Dictionary {
    private SessionFactory sessionFactory;
    private Checker checker;
    private String name;
    private int type;
    private List<Property> dictionary;

    public DBProperties(SessionFactory sessionFactory, int keyLength, String keySymbols, String name, int type) {
        this.sessionFactory = sessionFactory;
        this.name = name;
        this.type = type;

        initDictionary();

        this.checker = new ValidChecker(dictionary, keyLength, keySymbols);
    }

    //region override
    @Override
    public synchronized List<Property> getDictionary() {
        return new LinkedList<>(dictionary);
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized Property put(String key, String value) {
        if (!checker.isValidKey(key) || !checker.isValidValue(value))
            return checker.getResult();

        Session session = sessionFactory.getCurrentSession();
        Property record;

        if (checker.containsKey(key))
            record = dictionary.get((int) checker.getResult().getId());
        else {
            record = new DictionaryRecord();
            record.setKey(key);
            record.setType(type);
        }

        record.setValue(value);

        session.saveOrUpdate(record);

        dictionary.add(session
                .createQuery("from DictionaryRecord where key = :key", Property.class)
                .setParameter("key", key)
                .uniqueResult());

        return record;
    }

    @Override
    public synchronized Property get(String key) {
        if (!checker.containsKey(key)) return checker.getResult();

        return dictionary.get((int) checker.getResult().getId());
    }

    @Override
    public synchronized Property remove(String key) {
        if (!checker.containsKey(key)) return checker.getResult();

        Session session = sessionFactory.getCurrentSession();
        Property record = dictionary.remove((int) checker.getResult().getId());

        session.delete(record);

        return record;
    }
    //endregion

    private void initDictionary() {
        Session session = sessionFactory.openSession();
        dictionary = session
                .createQuery("from DictionaryRecord where type = :type", Property.class)
                .setParameter("type", type)
                .getResultList();
    }
}
