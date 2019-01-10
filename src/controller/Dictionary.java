package controller;

import java.nio.file.Path;
import java.util.Map;

public interface Dictionary {
    Map<String, String> getDictionary();
    String getName();
    String getKeyRegex();
    int getKeyLength();
    String remove(String key);
    String get(String key);
    String put(String key, String value);
    void write();
}
