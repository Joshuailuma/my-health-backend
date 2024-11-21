package com.myhealth.customer.service;


import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.response.ApiResponseMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface CertificateService {
    ApiResponseMessage uploadCertificate(MultipartFile file) throws ApiError, IOException;
    ByteArrayResource downloadCertificate(String fileName) throws ApiError;
}