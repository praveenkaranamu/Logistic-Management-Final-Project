package com.example.logistics.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageUtil {

    private final Path uploadDirectory =
            Paths.get("uploads");

    public FileStorageUtil() {

        try {

            Files.createDirectories(uploadDirectory);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not create upload directory",
                    e);
        }
    }

    public String saveFile(MultipartFile file) {

        if (file == null ||
                file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File cannot be empty");
        }

        String originalName =
                file.getOriginalFilename();

        if (originalName == null ||
                originalName.isBlank()) {

            throw new IllegalArgumentException(
                    "Invalid file name");
        }

        String lower =
                originalName.toLowerCase();

        if (!lower.endsWith(".pdf") &&
            !lower.endsWith(".jpg") &&
            !lower.endsWith(".jpeg") &&
            !lower.endsWith(".png")) {

            throw new IllegalArgumentException(
                    "Only PDF, JPG, JPEG and PNG files are allowed");
        }

        try {

            Path target =
                    uploadDirectory.resolve(
                            Paths.get(originalName)
                                  .getFileName()
                                  .toString());

            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING);

            return target.getFileName().toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not save file",
                    e);
        }
    }

    public byte[] getFile(String fileName) {

        try {

            Path file =
                    uploadDirectory.resolve(fileName);

            return Files.readAllBytes(file);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not read file",
                    e);
        }
    }
}