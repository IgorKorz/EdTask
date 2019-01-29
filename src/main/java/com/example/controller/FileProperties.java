package com.example.controller;

import com.example.model.*;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import java.util.LinkedList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileProperties implements Dictionary {
    private String name;
    private Checker checker;
    private Path source;
    private List<Property> dictionary;

    public FileProperties(String filePath, int keyLength, String keySymbols, String name) {
        this.name = name;
        initSource(filePath);
        initDictionary();
        this.checker = new ValidChecker(dictionary, keyLength, keySymbols);
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
        if (!checker.isValidKey(key)) return checker.getResult();

        Property record;

        if (checker.containsKey(key)) {
            record = dictionary.get((int) checker.getResult().getId());
            record.setValue(value);
        } else {
            record = new DictionaryRecord();
            record.setKey(key);
            record.setValue(value);

            dictionary.add(record);
        }

        writeToFile();

        return record;
    }

    @Override
    public Property get(String key) {
        if (!checker.containsKey(key)) return checker.getResult();

        return dictionary.get((int) checker.getResult().getId());
    }

    @Override
    public Property remove(String key) {
        if (!checker.containsKey(key)) return checker.getResult();

        Property record = dictionary.get((int) checker.getResult().getId());
        dictionary.remove((int) checker.getResult().getId());

        if (record != null)
            writeToFile();

        return record;
    }

    private void initDictionary() {
        dictionary = new LinkedList<>();

        try {
            List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);

            for (String line : lines) {
                String[] keyValue = line.split("=");
                DictionaryRecord record = new DictionaryRecord();
                record.setKey(keyValue[0]);
                record.setValue(keyValue[1]);

                dictionary.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(source)) {
            for (Property record : dictionary)
                bufferedWriter.write(record.getKey() + "=" + record.getValue() + "\n");
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
}
