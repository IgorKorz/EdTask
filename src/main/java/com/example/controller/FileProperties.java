package com.example.controller;

import com.example.model.*;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileProperties implements Dictionary {
  private final int keyLength;
  private final String keyRegex;
  private final String name;
  private Result result;
  private Source<Path> file;
  private Map<String, String> dictionary;

  public FileProperties(String filePath, int keyLength, String keyRegex, String name) throws IOException {
    if (keyLength <= 0) this.keyLength = 1;
    else this.keyLength = keyLength;

    Checker checker = new ValidChecker(this);
    file = new FileSource(filePath);
    dictionary = new LinkedHashMap<>();
    this.keyRegex = keyRegex;
    this.name = name;
    List<String> lines = Files.readAllLines(file.getSource(), StandardCharsets.UTF_8);

    for (String line : lines) {
      String[] keyValue = line.split("=");
      dictionary.put(keyValue[0], keyValue[1]);
    }
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
    String value = dictionary.remove(key);
    result.resultForRemove(key, value);

    return result.getResult();
  }

  @Override
  public String get(String key) {
    String value = dictionary.get(key);
    result.resultForGet(key, value);

    return result.getResult();
  }

  @Override
  public String put(String key, String value) {
    dictionary.put(key, value);
    result.resultForPut(key, value);

    return result.getResult();
  }

  @Override
  public void write() {
    try (BufferedWriter fileWriter = Files.newBufferedWriter(file.getSource())) {
      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
        fileWriter.write(entry.getKey() + "=" + entry.getValue());
        fileWriter.newLine();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
