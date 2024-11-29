package com.myhealth.customer.service.impl;


import com.myhealth.customer.config.KeycloakConfig;
import com.myhealth.customer.service.KeycloakService;
import com.myhealth.library.exception.ApiError;
import com.myhealth.library.model.request.LoginRequestDto;
import com.myhealth.library.model.request.LoginResponseDto;
import com.myhealth.library.model.request.RegistrationRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.user-client-id}")
    private String userClientId;
    @Value("${keycloak.domain}")
    private String domain;
    @Value("${keycloak.admin-client-secret}")
    private String secretKey;

    public final KeycloakConfig keycloakConfig;

    @Override
    public String createUser(RegistrationRequest registrationRequest) throws ApiError {
        String registrationResponse = "";
        UserRepresentation userRepresentation = getUserRepresentation(registrationRequest);
        log.info("Realm name is "+realm);
        RealmResource realmResource = keycloakConfig.keycloak().realm(realm);
        List<UserRepresentation> io = realmResource.users().list();
        UsersResource usersResource = realmResource.users();
        try (Response response = usersResource.create(userRepresentation)) {
            if(Objects.equals(201, response.getStatus())){
                registrationResponse = "Registration successful";

            }
        } catch (Exception e){
            registrationResponse = "Registration failed";
            log.info("Registration failed ",e);
            throw new ApiError(registrationResponse, "02", HttpStatus.NOT_FOUND);
        }
        return registrationResponse;
    }

    private static UserRepresentation getUserRepresentation(RegistrationRequest registrationRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registrationRequest.getEmailAddress());
        userRepresentation.setFirstName(registrationRequest.getFirstName());
        userRepresentation.setLastName(registrationRequest.getLastName());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setUsername(registrationRequest.getEmailAddress());
        userRepresentation.setEnabled(true);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(registrationRequest.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        List<CredentialRepresentation> credentialRepresentationList = new ArrayList<>();
        credentialRepresentationList.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialRepresentationList);
        return userRepresentation;
    }

    @Override
    public UserRepresentation getUserById(String id) {
        RealmResource realmResource = keycloakConfig.keycloak().realm(realm);
       return realmResource.users().get(id).toRepresentation();
    }

    @Override
    public void deleteUser(String id) {
        RealmResource realmResource = keycloakConfig.keycloak().realm(realm);

        try (Response deleteResponse = realmResource.users().delete(id)) {
            if (deleteResponse.getStatus()==200){
                log.info("User deleted");
            }
        } catch (Exception e){
            throw new RuntimeException("Couldn't delete user ",e);
        }
    }

    public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) throws ApiError {
        try (Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(domain)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(userClientId)
                .clientSecret(secretKey)
                .username(loginRequestDto.getEmailAddress())
                .password(loginRequestDto.getPassword())
                .build()) {
            try {
                AccessTokenResponse accessToken = kc.tokenManager().getAccessToken();

                return LoginResponseDto.builder()
                        .accessToken(accessToken.getToken())
                        .expiresIn(String.valueOf(accessToken.getExpiresIn()))
                        .refreshToken(accessToken.getRefreshToken())
                        .refreshExpiresIn(String.valueOf(accessToken.getRefreshExpiresIn()))
                        .build();
            } catch (Exception e) {
                log.info("Login failed ", e);
                throw new ApiError("Invalid credentials", "02", HttpStatus.BAD_REQUEST);
            }
        }
    }
    }
