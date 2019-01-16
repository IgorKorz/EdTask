package com.example.controller;

import com.example.model.*;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileProperties implements Dictionary {
  private final int keyLength;
  private final String keyRegex;
  private final String name;
  private Checker checker;
  private Path source;
  private Map<String, String> dictionary;

  public FileProperties(String filePath, int keyLength, String keyRegex, String name) throws IOException {
    if (keyLength <= 0) this.keyLength = 1;
    else this.keyLength = keyLength;

    this.keyRegex = keyRegex;
    this.name = name;
    this.checker = new ValidChecker(this);
    initSource(filePath);
    initDictionary();
  }

  @Override
  public Map<String, String> getDictionary() {
    return new LinkedHashMap<>(dictionary);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getKeyRegex() {
    return keyRegex;
  }

  @Override
  public int getKeyLength() {
    return keyLength;
  }

  @Override
  public String remove(String key) {
    if (checker.keyContains(key)) {
      String value = dictionary.remove(key);

      writeToFile();

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
      dictionary.put(key, value);

      writeToFile();

      return checker.resultForPut(key, value);
    } else return checker.getResult();
  }

  private void writeToFile() {
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(source)) {
      for (Map.Entry<String, String> entry : dictionary.entrySet())
        bufferedWriter.write(entry.getKey() + "=" + entry.getValue() + "\n");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  private void initDictionary() {
    dictionary = new LinkedHashMap<>();

    try {
      List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);

      for (String line : lines) {
        String[] keyValue = line.split("=");
        dictionary.put(keyValue[0], keyValue[1]);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
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
