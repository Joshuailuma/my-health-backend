package com.myhealth.customer.service.impl;

import com.myhealth.customer.service.CertificateService;
import com.myhealth.customer.utils.FileValidator;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class CertificateServiceImpl implements CertificateService {
    private final S3AsyncClient s3AsyncClient;
    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public Mono<ApiResponseMessage> uploadCertificate(MultipartFile file) throws ApiError, IOException {

        FileValidator.validateImageFile(file);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Convert MultipartFile to ByteBuffer
        ByteBuffer buffer;
        try {
            buffer = ByteBuffer.wrap(file.getBytes());
        } catch (Exception e) {
            return Mono.error(new ApiError("Failed to read file content", "012", HttpStatus.BAD_REQUEST));
        }


        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        // Upload file to S3 asynchronously
        CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(
                putObjectRequest,
                AsyncRequestBody.fromByteBuffer(buffer)
        );

        // Convert CompletableFuture to Mono
        return Mono.fromFuture(future)
                .map(response -> new ApiResponseMessage("Upload successful", String.format("https://%s.s3.%s.amazonaws.com/%s",
                        bucketName,
                        s3AsyncClient.serviceClientConfiguration().region().toString(),
                        fileName)));
    }

    @Override
    public ByteArrayResource downloadCertificate(String fileName) throws ApiError {
        return null;
    }
}
