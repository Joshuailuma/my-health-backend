package com.myhealth.customer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class S3Service {
    private final S3Client s3Client;
    @Value("cloud.aws.bucket.name")
    private String bucketName;
    @Value("cloud.aws.region.static")
    private String region;
    
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String key = folder + "/" + uniqueFileName;

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    Paths.get(file.getOriginalFilename())
            );
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage(), e);
        }

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
    }
}
