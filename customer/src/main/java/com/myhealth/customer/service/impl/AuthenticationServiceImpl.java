package com.myhealth.customer.service.impl;

import com.myhealth.customer.entity.Doctor;
import com.myhealth.customer.entity.Patient;
import com.myhealth.customer.entity.User;
import com.myhealth.customer.repository.DoctorRepository;
import com.myhealth.customer.repository.PatientRepository;
import com.myhealth.customer.repository.UserRepository;
import com.myhealth.customer.service.AuthenticationService;
import com.myhealth.customer.service.EmailService;
import com.myhealth.library.enums.ROLE;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.*;
import com.myhealth.library.model.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.myhealth.library.utils.AppUtils.generateOtp;
import static com.myhealth.library.utils.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    public final KeycloakServiceImpl keycloakService;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final EmailService emailService;

    // Save registration details to the database
    public Mono<ApiResponseMessage<Object>> register(RegistrationRequest registrationRequest) throws ApiError {
        try {
            return userRepository.findByEmail(registrationRequest.getEmailAddress())
                    .flatMap(existingPatient -> Mono.error(new ApiError("User with email already exists", "005", HttpStatus.CONFLICT)))
                    .switchIfEmpty(Mono.defer(() -> {

                        if (registrationRequest.getRole() != ROLE.PATIENT && registrationRequest.getRole() != ROLE.DOCTOR) {
                            return Mono.error(new ApiError(INVALID_ROLE, "006", HttpStatus.BAD_REQUEST));
                        }

                        keycloakService.createUser(registrationRequest);
                        User user = getUser(registrationRequest);

                        return userRepository.save(user)
                                .flatMap(savedUser -> {
                                    if (registrationRequest.getRole() == ROLE.PATIENT) {
                                        Patient patient = new Patient();
                                        patient.setUserId(savedUser.getId());
                                        return patientRepository.save(patient);
                                    } else {
                                        Doctor doctor = new Doctor();
                                        doctor.setUserId(savedUser.getId());
                                        return doctorRepository.save(doctor);
                                    }
                                });
                    }))
                    .map(data -> new ApiResponseMessage<>(REGISTRATION_SUCCESSFUL, data))
                    .onErrorResume(ApiError.class, Mono::error);
        } catch (Exception e) {
            log.info("Registration exception is: " + e);
            throw new ApiError(REGISTRATION_FAILED, "01", HttpStatus.BAD_REQUEST);
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

    public Mono<ApiResponseMessage<Object>> resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws ApiError{
        String generatedOtp = generateOtp();

       return  userRepository.findByEmail(passwordResetRequestDto.email())
               .switchIfEmpty(Mono.error(new ApiError(USER_DOES_NOT_EXIST, "010", HttpStatus.NOT_FOUND)))
               .flatMap(user -> {

                   // Save user otp to the table here
                   user.setOtp(generateOtp());
                   user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));
                  return userRepository.save(user)
                          .flatMap(savedUser -> emailService.sendOtpEmail(passwordResetRequestDto.email(), generatedOtp, "ResetPasswordOtpEmail","Reset Password")
                          );
                         }
               )
               .then(Mono.just(new ApiResponseMessage<Object>(OTP_SENT_TO_MAIL, null)))
               .onErrorResume(ApiError.class, error -> Mono.just(new ApiResponseMessage<>(error.getMessage(), null))
               );
}

    @Override
    public Mono<ApiResponseMessage<Object>> validatePasswordReset(ValidatePasswordResetDto validatePasswordResetDto) {
       // Grab OTP, |Check OTP From  database,| pull the user associated
        return userRepository.findByEmail(validatePasswordResetDto.email()).switchIfEmpty(
                Mono.error(new ApiError(USER_DOES_NOT_EXIST, "010", HttpStatus.NOT_FOUND))
        ).flatMap( user -> {
           if(!Objects.equals(user.getOtp(), validatePasswordResetDto.otp())){
              return Mono.error(new ApiError(OTP_MISMATCH, "011", HttpStatus.BAD_REQUEST));
           }
           if(LocalDateTime.now().isAfter(user.getOtpExpiryTime()) ){
               return Mono.error(new ApiError(OTP_EXPIRED, "012", HttpStatus.BAD_REQUEST));
           }
           // Reset the password using keycloak
             return keycloakService.resetPassword(validatePasswordResetDto)
                     .then(Mono.defer(()-> {
                         user.setOtp(null);
                         user.setOtpExpiryTime(null);
                         return userRepository.save(user);
                     }));

                }).then(Mono.just(new ApiResponseMessage<Object>(PASSWORD_RESET_SUCCESSFUL, null)))
                .onErrorResume(ApiError.class, error -> Mono.just(new ApiResponseMessage<>(error.getMessage(), null))
                );
    }
}
