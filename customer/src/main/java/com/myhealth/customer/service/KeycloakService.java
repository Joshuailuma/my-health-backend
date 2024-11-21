package com.myhealth.customer.service;

import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.RegistrationRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    String createUser(RegistrationRequest registrationRequest) throws ApiError;
    UserRepresentation getUserById(String id);
    void deleteUser(String id);
}
