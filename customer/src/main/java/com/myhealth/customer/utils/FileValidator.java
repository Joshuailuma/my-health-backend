package com.myhealth.customer.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileValidator {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    public static void validateImageFile(MultipartFile file) throws IllegalArgumentException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Check file size 5 MB limit
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds the limit (5 MB)");
        }

        // Check file type (only images allowed)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        // Check file extension
        String fileName = file.getOriginalFilename();
        if (fileName == null || !ALLOWED_EXTENSIONS.contains(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase())) {
            throw new IllegalArgumentException("Only JPG, JPEG, and PNG files are allowed");
        }
    }
}
