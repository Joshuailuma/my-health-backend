package com.myhealth.customer.controller;

import com.myhealth.customer.service.S3Service;
import com.myhealth.library.model.response.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping(value = "/customer/doctor" )
public class DoctorController {
    private final S3Service s3Service;

    public DoctorController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/certificate")
    public ResponseEntity<ApiResponseMessage<String>> uploadCertificate(MultipartFile multipartFile) throws IOException {
        String uploadCertificateResponse = s3Service.uploadFile(multipartFile, "/certificate");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseMessage<>(uploadCertificateResponse, null));
    }
}
