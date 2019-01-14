package com.example.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSource implements Source<Path> {
    private Path source;

    public FileSource(String filePath) {
        source = Paths.get(filePath);

        if (!Files.exists(source)) try {
            source = Files.createFile(source);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Path getSource() {
        return source;
    }
}
