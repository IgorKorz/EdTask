package model;

import java.nio.file.Path;

public interface Checker {
    Path checkExistAndGetFile(String filePath);
    boolean isValidKey(String key);
}
