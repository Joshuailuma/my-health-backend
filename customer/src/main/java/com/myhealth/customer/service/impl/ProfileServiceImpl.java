package com.myhealth.customer.service.impl;

import com.myhealth.customer.entity.UserDetails;
import com.myhealth.customer.repository.DoctorRepository;
import com.myhealth.customer.repository.PatientRepository;
import com.myhealth.customer.repository.UserRepository;
import com.myhealth.customer.service.KeycloakService;
import com.myhealth.customer.service.ProfileService;
import com.myhealth.library.enums.ROLE;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.EditProfileRequest;
import com.myhealth.library.model.response.ApiResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.myhealth.library.utils.Messages.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final KeycloakService keycloakService;
    @Value("cloud.aws.bucket.name")
    private String bucketName;

    ProfileServiceImpl(PatientRepository patientRepository,
                       UserRepository userRepository,
                       DoctorRepository doctorRepository,
                       KeycloakService keycloakService) {

        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public Mono<ApiResponseMessage<UserDetails>> viewProfile(String id) {

        return userRepository.findById(UUID.fromString(id))
                .flatMap(user -> {
                    if (user.getRole() == ROLE.PATIENT) {
                        return patientRepository.findById(user.getId())
                                .map(patient -> new UserDetails(user, patient))
                                .map(userDetails -> new ApiResponseMessage<>(PROFILE_RETRIEVED, userDetails));

                    } else if (user.getRole() == ROLE.DOCTOR) {
                        return doctorRepository.findById(user.getId())
                                .map(doctor -> new UserDetails(user, doctor))
                                .map(userDetails -> new ApiResponseMessage<>(PROFILE_RETRIEVED, userDetails));
                    } else {
                        return Mono.error(new ApiError(INVALID_ROLE, "006", HttpStatus.BAD_REQUEST));
                    }
                }).switchIfEmpty(
                        Mono.error(new ApiError(USER_NOT_FOUND, "006", HttpStatus.BAD_REQUEST)));

//       new ApiResponseMessage<>("Profile retrieved successfully", userDetails);

//        Mono<Patient> patientFlux = patientRepository.findById(UUID.fromString(id));
    }

    @Override
    public ApiResponseMessage<String> uploadProfilePhoto(MultipartFile multipartFile) {
        return null;
    }

//    @Override
//    public ApiResponseMessage<String> uploadProfilePhoto(MultipartFile multipartFile) {
//        String key = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
//
//        try {
//            Path tempFile = Files.createTempFile("photo-", multipartFile.getOriginalFilename());
//            multipartFile.transferTo(tempFile);
//
//            s3Client.putObject(
//                    PutObjectRequest.builder()
//                            .bucket(bucketName)
//                            .key(key)
//                            .build(),
//                    tempFile
//            );
//
//            Files.delete(tempFile); // Clean up the temporary file
//        } catch (IOException e) {
//            throw new ApiError(FAILED_TO_UPLOAD_IMAGE + e, "008", HttpStatus.BAD_REQUEST);
//        }
//        return new ApiResponseMessage<>(PROFILE_RETRIEVED, null);
//    }

    @Override
    public ApiResponseMessage<String> editProfile(EditProfileRequest request) {
        keycloakService.getLoggedInUser();
//        userRepository.save()
        return null;
    }

}
