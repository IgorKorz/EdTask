package com.example.model;

import com.example.controller.Dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DictionaryChecker implements Checker {
    private Dictionary dictionary;

    public DictionaryChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public Path checkExistAndGetFile(String filePath) {
        Path file = Paths.get(filePath);

        if (!Files.exists(file)) try {
            file = Files.createFile(file);
        } catch (IOException e) {
            System.out.println(e);
        }

        return file;
    }

    @Override
    public boolean isValidKey(String key) {
        return key.length() == dictionary.getKeyLength() && key.matches(dictionary.getKeyRegex());
    }
}