package com.example.controller;

import com.example.model.*;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
public class DBProperties implements Dictionary {
    private DataSource dataSource;
    private Checker checker;
    private String name;
    private int type;
    private List<Property> dictionary;

    public DBProperties(DataSource dataSource, int keyLength, String keySymbols, String name, int type) {
        this.dataSource = dataSource;
        this.name = name;
        this.type = type;

        initDictionary();

        this.checker = new ValidChecker(dictionary, keyLength, keySymbols);
    }

    //region override
    @Override
    public synchronized List<Property> getDictionary() {
        return new ArrayList<>(dictionary);
    }

    @Override
    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized Property put(String key, String value) {
        if (!checker.isValidKey(key)) return checker.getResult();

        if (!checker.isValidValue(value)) return checker.getResult();

        int recordPos = checker.containsKey(key);
        String sql = recordPos > -1
                ? "UPDATE public.dictionary SET value = ? WHERE key = ?"
                : "INSERT INTO public.dictionary (key, value, type) VALUES (?, ?, ?)";
        Property record;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (recordPos > -1) {
                record = dictionary.get(recordPos);
                record.setValue(value);

                statement.setString(1, value);
                statement.setString(2, key);
                statement.executeUpdate();
            } else {
                record = new DictionaryRecord(key, value, type);

                statement.setString(1, key);
                statement.setString(2, value);
                statement.setInt(3, type);
                statement.executeUpdate();

                dictionary.add(record);
            }

            return record;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

            return new ErrorProperty(e.getSQLState(), e.getMessage());
        }
    }

    @Override
    public synchronized Property get(String key) {
        int recordPos = checker.containsKey(key);

        if (recordPos == -1) return checker.getResult();

        return dictionary.get(recordPos);
    }

    @Override
    public synchronized Property remove(String key) {
        int recordPos = checker.containsKey(key);

        if (recordPos == -1) return checker.getResult();

        Property record = dictionary.remove(recordPos);
        String sql = "DELETE FROM public.dictionary WHERE key = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, record.getKey());
            statement.executeUpdate();

            return record;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

            return new ErrorProperty(e.getSQLState(), e.getMessage());
        }
    }
    //endregion

    private void initDictionary() {
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS public.dictionary " +
                    "(" +
                        "id bigint NOT NULL DEFAULT nextval('dictionary_id_seq'::regclass)," +
                    "    key character varying(255) NOT NULL," +
                    "    value character varying(255) NOT NULL," +
                    "    type integer NOT NULL," +
                    "    CONSTRAINT id PRIMARY KEY (id)," +
                    "    CONSTRAINT key UNIQUE (key, type)" +
                    ")");

            try (ResultSet set = statement.executeQuery("SELECT * FROM public.dictionary WHERE type = " + type)) {
                dictionary = Collections.synchronizedList(new ArrayList<>());

                while (set.next()) {
                    Property record = new DictionaryRecord();
                    record.setId(set.getLong("id"));
                    record.setKey(set.getString("key"));
                    record.setValue(set.getString("value"));
                    record.setType(set.getInt("type"));

                    dictionary.add(record);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
