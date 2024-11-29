package com.myhealth.customer.service.impl;

import com.myhealth.customer.repository.DoctorRepository;
import com.myhealth.customer.repository.PatientRepository;
import com.myhealth.customer.repository.UserRepository;
import com.myhealth.customer.service.ProfileService;
import com.myhealth.library.enums.ROLE;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.response.ApiResponseMessage;
import com.myhealth.customer.entity.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private

    ProfileServiceImpl(PatientRepository patientRepository,
                       UserRepository userRepository,
                       DoctorRepository doctorRepository) {

        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public ApiResponseMessage<UserDetails> viewProfile(String id) {

      Mono<UserDetails> userDetails =   userRepository.findById(UUID.fromString(id))
                .flatMap(user -> {
                    if (user.getRole() == ROLE.PATIENT) {
                        return patientRepository.findById(user.getId())
                                .map( patient -> new UserDetails(user, patient));

                    } else if (user.getRole() == ROLE.DOCTOR) {
                        return doctorRepository.findById(user.getId())
                                .map(doctor -> new UserDetails(user, doctor));
                    } else {
                        return Mono.error(new ApiError("Invalid user role", "006", HttpStatus.BAD_REQUEST));
                    }
                }).switchIfEmpty(Mono.error(new ApiError("User not found", "006", HttpStatus.BAD_REQUEST)));

        new ApiResponseMessage<>("Profile retrieved successfully", userDetails);

//        Mono<Patient> patientFlux = patientRepository.findById(UUID.fromString(id));

        return null;
    }
}
