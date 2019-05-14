package com.example.controller;

import com.example.model.*;
import com.example.validation.Validator;
import com.example.validation.DictionaryValidator;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import java.util.LinkedList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileDictionary implements Dictionary {
    private String name;
    private Path source;
    private List<Property> dictionary;
    private Validator checker;

    public FileDictionary(String filePath, int keyLength, String keySymbols, String name) {
        this.name = name;

        initSource(filePath);
        initDictionary();

        this.checker = new DictionaryValidator(dictionary, keyLength, keySymbols);
    }

    @Override
    public List<Property> getDictionary() {
        return new LinkedList<>(dictionary);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Property put(String key, String value) {
        if (!checker.isValidKey(key) || !checker.isValidValue(value))
            return checker.getResult();

        Property record;
        int recordPos = checker.containsKey(key);

        if (recordPos > -1) {
            record = dictionary.get(recordPos);
        } else {
            record = new DictionaryRecord();
            record.setKey(key);

            dictionary.add(record);
        }

        record.setValue(value);

        writeToFile();

        return record;
    }

    @Override
    public Property get(String key) {
        int recordPos = checker.containsKey(key);

        if (recordPos == -1) return checker.getResult();

        return dictionary.get(recordPos);
    }

    @Override
    public Property remove(String key) {
        int recordPos = checker.containsKey(key);

        if (recordPos == -1) return checker.getResult();

        Property record = dictionary.get(recordPos);
        dictionary.remove(recordPos);

        if (record != null)
            writeToFile();

        return record;
    }

    private void initDictionary() {
        dictionary = new LinkedList<>();

        try {
            List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);

            for (String line : lines) {
                String[] keyValueType = line.split("=");
                Property record = new DictionaryRecord();
                record.setKey(keyValueType[0]);
                record.setValue(keyValueType[1]);

                try {
                    record.setType(Integer.parseInt(keyValueType[2]));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid type-number for " + record.toString());
                }

                dictionary.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSource(String filePath) {
        source = Paths.get(filePath);

        if (!Files.exists(source)) try {
            source = Files.createFile(source);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeToFile() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(source)) {
            for (Property record : dictionary)
                bufferedWriter.write(record.getKey() + "=" + record.getValue() + "=" + record.getType() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
