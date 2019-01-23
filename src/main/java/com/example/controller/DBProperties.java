package com.example.controller;

import com.example.model.Checker;
import com.example.model.DictionaryRecord;
import com.example.model.ValidChecker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class DBProperties implements Dictionary {
    private SessionFactory dataSource;
    private Checker checker;
    private String name;
    private int type;
    private Map<String, String> dictionary;

    public DBProperties(SessionFactory dataSource, int keyLength, String keySymbols, String name, int type) {
        this.dataSource = dataSource;
        this.name = name;
        this.type = type;

        initDictionary();

        this.checker = new ValidChecker(dictionary, keyLength, keySymbols);
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
    public String get(String key) {
        if (!checker.keyContains(key)) return checker.getResult();

        String value = dictionary.get(key);

        return checker.resultForGet(key, value);
    }

    @Override
    public String put(String key, String value) {
        if (!checker.isValidKey(key)) return checker.getResult();

        Session session = dataSource.getCurrentSession();

        DictionaryRecord record;

        if (checker.keyContains(key)) {
            record = session
                    .createQuery("from DictionaryRecord where key = :key and type = :type", DictionaryRecord.class)
                    .setParameter("key", key)
                    .setParameter("type", type)
                    .uniqueResult();
            record.setValue(value);

            session.update(record);
        } else {
            record = new DictionaryRecord();
            record.setKey(key);
            record.setValue(value);
            record.setType(type);

            record.setKey(key);
            record.setValue(value);

            session.save(record);
        }

        dictionary.put(key, value);

        return checker.resultForPut(key, value);
    }

    @Override
    public String remove(String key) {
        if (!checker.keyContains(key)) return checker.getResult();

        Session session = dataSource.getCurrentSession();

        DictionaryRecord record = session
                .createQuery("from DictionaryRecord where key = :key and type = :type", DictionaryRecord.class)
                .setParameter("key", key)
                .setParameter("type", type)
                .getSingleResult();

        session.delete(record);

        return checker.resultForRemove(key, dictionary.remove(key));
    }

    private void initDictionary() {
        Session session = dataSource.openSession();
        dictionary = Collections.synchronizedMap(new LinkedHashMap<>());
        List<DictionaryRecord> recordList = session
                .createQuery("from DictionaryRecord where type = :type", DictionaryRecord.class)
                .setParameter("type", type)
                .list();

        for (DictionaryRecord record : recordList) {
            String key = record.getKey();
            String value = record.getValue();
            dictionary.put(key, value);
        }
    }
    //endregion
}
