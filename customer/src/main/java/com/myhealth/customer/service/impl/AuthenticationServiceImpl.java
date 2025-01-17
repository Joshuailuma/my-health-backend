package com.myhealth.customer.service.impl;

import com.myhealth.customer.entity.Doctor;
import com.myhealth.customer.entity.Patient;
import com.myhealth.customer.entity.User;
import com.myhealth.customer.repository.DoctorRepository;
import com.myhealth.customer.repository.PatientRepository;
import com.myhealth.customer.repository.UserRepository;
import com.myhealth.customer.service.AuthenticationService;
import com.myhealth.library.enums.ROLE;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.LoginRequestDto;
import com.myhealth.library.model.request.LoginResponseDto;
import com.myhealth.library.model.request.RegistrationRequest;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    public final KeycloakServiceImpl keycloakService;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    // Save registration details to the database
    public Mono<ApiResponseMessage<Object>> register(RegistrationRequest registrationRequest) throws ApiError {
        try {
            keycloakService.createUser(registrationRequest);

            return userRepository.findByEmail(registrationRequest.getEmailAddress())
                    .flatMap(existingPatient -> Mono.error(new ApiError("User with email already exists", "005", HttpStatus.CONFLICT))
                    )
                    .switchIfEmpty(
                            Mono.defer(() -> {
                                User user = getUser(registrationRequest);
                               return userRepository.save(user).flatMap(
                                       savedUser -> {
                                if(registrationRequest.getRole() == ROLE.PATIENT){
                                    Patient patient = new Patient();
                                    patient.setUserId(user.getId());
                                    return patientRepository.save(patient);
                            } else if (registrationRequest.getRole() == ROLE.DOCTOR) {
                                    Doctor doctor = new Doctor();
                                    doctor.setUserId(user.getId());
                                   return doctorRepository.save(doctor);
                                } else {
                                    return Mono.error(new ApiError("Invalid user role", "006", HttpStatus.BAD_REQUEST));
                                }
                                       }
                               );

                            })
                    ).map(data -> new ApiResponseMessage<>("Registration successful", data))
                    .onErrorResume(ApiError.class, error -> {
                        return Mono.just(new ApiResponseMessage<>(error.getMessage(), null));
                    });
        } catch (Exception e){
            log.info("Registration exception is "+e);
            throw new ApiError("Registration failed", "01", HttpStatus.BAD_REQUEST);
        }
    }

    private static User getUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setPhoneNumber(registrationRequest.getPhoneNumber());
        user.setCountryCode(registrationRequest.getCountryCode());
        user.setGender(registrationRequest.getGender());
        user.setRole(registrationRequest.getRole());
        user.setEmail(registrationRequest.getEmailAddress());
        user.setDateOfBirth(registrationRequest.getDateOfBirth());
        return user;
    }


    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiError {
        return keycloakService.authenticateUser(loginRequestDto);

    }
}
