package com.myhealth.customer.controller;

import com.myhealth.customer.service.CertificateService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer/doctor")
public class DoctorController {
    private final CertificateService certificateService;

    @PostMapping(value = "/upload-certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ApiResponseMessage>> uploadCertificate(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return certificateService.uploadCertificate(multipartFile)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .onErrorResume(ApiError.class, e -> Mono.just(
                        ResponseEntity.status(e.getHttpStatus())
                                .body(new ApiResponseMessage<>(e.getMessage(), null))));
    }
}
