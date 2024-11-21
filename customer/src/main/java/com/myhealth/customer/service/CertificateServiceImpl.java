package com.myhealth.customer.service;

import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CertificateServiceImpl implements CertificateService{
    private final S3Client s3Client;
    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Override
    public ApiResponseMessage uploadCertificate(MultipartFile file) throws ApiError, IOException {
        String key = Objects.requireNonNull(file.getOriginalFilename());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .acl("public-read")
                .build();

        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Return the URL of the uploaded file
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, s3Client.region().id(), key);
        return new ApiResponseMessage("success");
    }

    @Override
    public ByteArrayResource downloadCertificate(String fileName) throws ApiError {
        return null;
    }
}
