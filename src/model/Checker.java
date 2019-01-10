package model;

import controller.Dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Checker {
    public static Path checkExistAndGetFile(String filePath) {
        Path file = Paths.get(filePath);

        if (!Files.exists(file)) try {
            file = Files.createFile(file);
        } catch (IOException e) {
            System.out.println(e);
        }

        return file;
    }

    public static boolean isValidKey(Dictionary dictionary, String key) {
        return key.length() == dictionary.getKeyLength() && key.matches(dictionary.getKeyRegex());
    }
}