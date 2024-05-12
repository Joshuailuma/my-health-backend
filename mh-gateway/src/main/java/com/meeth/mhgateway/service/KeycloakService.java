package com.meeth.mhgateway.service;


import com.meeth.mhgateway.exception.CustomError;
import com.meeth.mhgateway.model.request.RegistrationRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    String createUser(RegistrationRequest registrationRequest) throws CustomError;
    UserRepresentation getUserById(String id);
    void deleteUser(String id);
}
