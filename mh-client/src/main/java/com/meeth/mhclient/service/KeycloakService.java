package com.meeth.mhclient.service;


import com.meeth.mhclient.exception.CustomError;
import com.meeth.mhclient.model.request.RegistrationRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    String createUser(RegistrationRequest registrationRequest) throws CustomError;
    UserRepresentation getUserById(String id);
    void deleteUser(String id);
}
