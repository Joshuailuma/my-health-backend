package com.myhealth.customer;

import com.myhealth.customer.entity.User;
import com.myhealth.customer.repository.DoctorRepository;
import com.myhealth.customer.repository.PatientRepository;
import com.myhealth.customer.repository.UserRepository;
import com.myhealth.customer.service.KeycloakService;

import com.myhealth.customer.service.impl.AuthenticationServiceImpl;
import com.myhealth.customer.service.impl.KeycloakServiceImpl;
import com.myhealth.library.enums.Gender;
import com.myhealth.library.model.request.RegistrationRequest;

import java. time. LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static com.myhealth.library.enums.ROLE.PATIENT;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private KeycloakServiceImpl keycloakService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    RegistrationRequest request = new RegistrationRequest(
            "Joshua",
            "Iluma",
            "joshua@example.com",
            "12345678",
            LocalDate.now(),
            "+234",
            Gender.MALE,
            "12345678",
            PATIENT
    );
    @Test
    void shouldReturnErrorWhenUserAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(request.getEmailAddress()))
                .thenReturn(Mono.just(new User()));

        // Act
        StepVerifier.create(authenticationService.register(request))
                .expectNextMatches(response -> response.getMessage().equals("User with email already exists"))
                .verifyComplete();

        // Verify interactions
        verify(userRepository).findByEmail(request.getEmailAddress());
        verifyNoMoreInteractions(userRepository, patientRepository, doctorRepository, keycloakService);
    }
}