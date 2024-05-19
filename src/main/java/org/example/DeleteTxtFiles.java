package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DeleteTxtFiles {
    public static void main(String[] args) {
        // Ana klasör yolunu buraya girin
        Path rootPath = Paths.get("C:\\example\\directory");

        try (Stream<Path> walk = Files.walk(rootPath)) {
            walk.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> {
                        try {
                            System.out.println("Deleting: " + path);
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete " + path + ": " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
