package com.myhealth.customer.service;

import com.myhealth.customer.entity.Patient;
import com.myhealth.customer.repository.PatientRepository;
import com.myhealth.library.enums.RegistrationStatus;
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
public class AuthenticationServiceImpl implements AuthenticationService{
    public final KeycloakServiceImpl keycloakService;
    private final PatientRepository patientRepository;

    // Save registration details to the database
    public ApiResponseMessage register(RegistrationRequest registrationRequest) throws ApiError {
        try {
            keycloakService.createUser(registrationRequest);

            Mono<Object> patientMono = patientRepository.findByEmail(registrationRequest.getEmailAddress())
                    .flatMap(existingPatient -> Mono.error(new ApiError("User with email already exists", "005", HttpStatus.CONFLICT)))
                    .switchIfEmpty(
                            Mono.defer(() -> {
                                Patient newPatient = new Patient();
                                newPatient.setEmail(registrationRequest.getEmailAddress());
                                newPatient.setFirstName(registrationRequest.getFirstName());
                                newPatient.setLastName(registrationRequest.getLastName());
                                newPatient.setPhoneNumber(registrationRequest.getPhoneNumber());
                                newPatient.setRegistrationStatus(RegistrationStatus.INCOMPLETE);
                                newPatient.setCountryCode(registrationRequest.getCountryCode());
                                newPatient.setGender(registrationRequest.getGender());
                                return patientRepository.save(newPatient);
                            })
                    );

//            *346*3*NIN*8752557  TO GENERATE VNIN, LIGHT BILL, UTILITY BILL,

//         throw new ApiError("This account already present", "04", HttpStatus.CONFLICT);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
            apiResponseMessage.setMessage("Registration successful");
            return apiResponseMessage;
        } catch (Exception e){
            log.info("Registration exception is "+e);
            throw new ApiError("Registration failed", "01", HttpStatus.BAD_REQUEST);
        }
    }


    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiError {
        return keycloakService.authenticateUser(loginRequestDto);

    }
}
