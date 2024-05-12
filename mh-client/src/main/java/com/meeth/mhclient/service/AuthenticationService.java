package com.meeth.mhclient.service;

import com.meeth.mhclient.entity.Patient;
import com.meeth.mhclient.enums.RegistrationStatus;
import com.meeth.mhclient.exception.CustomError;
import com.meeth.mhclient.model.request.LoginRequestDto;
import com.meeth.mhclient.model.request.LoginResponseDto;
import com.meeth.mhclient.model.request.RegistrationRequest;
import com.meeth.mhclient.model.response.CustomResponseMessage;
import com.meeth.mhclient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public final KeycloakServiceImpl keycloakService;
    private final PatientRepository patientRepository;

    // Save registration details to the database
    public CustomResponseMessage register(RegistrationRequest registrationRequest) throws CustomError {
        try {
            keycloakService.createUser(registrationRequest);
            Patient patient = Patient.builder()
                    .email(registrationRequest.getEmailAddress())
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .phoneNumber(registrationRequest.getPhoneNumber())
                    .dateOfBirth(registrationRequest.getDateOfBirth())
                    .countryCode(registrationRequest.getCountryCode())
                    .gender(registrationRequest.getGender())
                    .registrationStatus(RegistrationStatus.COMPLETE)
                    .build();
            Optional<Patient> user = patientRepository.findByEmail(registrationRequest.getEmailAddress());
            if (user.isPresent()){
                throw new CustomError("This account already present", "04", HttpStatus.CONFLICT);
            }
            patientRepository.save(patient);
           CustomResponseMessage customResponseMessage = new CustomResponseMessage();
           customResponseMessage.setMessage("Registration successful");
            return customResponseMessage;
        } catch (Exception e){
            log.info("Registration exception is "+e);
            throw new CustomError("Registration failed", "01", HttpStatus.BAD_REQUEST);
        }

    }


    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws CustomError {
           return keycloakService.authenticateUser(loginRequestDto);
    }
}
