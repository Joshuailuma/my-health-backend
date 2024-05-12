package com.meeth.mhclient.service;

import com.meeth.mhclient.config.KeycloakConfig;
import com.meeth.mhclient.exception.CustomError;
import com.meeth.mhclient.model.request.LoginRequestDto;
import com.meeth.mhclient.model.request.LoginResponseDto;
import com.meeth.mhclient.model.request.RegistrationRequest;
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
    public final KeycloakConfig keycloakConfig;
    public final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.user-client-id}")
    private String userClientId;
    @Value("${keycloak.domain}")
    private String domain;
    @Override
    public String createUser(RegistrationRequest registrationRequest) throws CustomError {
        String registrationResponse = "";
        UserRepresentation userRepresentation = getUserRepresentation(registrationRequest);
        log.info("Realm name is "+realm);
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation>io  = realmResource.users().list();
        UsersResource usersResource = realmResource.users();
        try (Response response = usersResource.create(userRepresentation)) {
            if(Objects.equals(201, response.getStatus())){
                registrationResponse = "Registration successful";

            }
        } catch (Exception e){
            registrationResponse = "Registration failed";
            log.info("Registration failed ",e);
            throw new CustomError(registrationResponse, "02", HttpStatus.BAD_REQUEST);
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
        RealmResource realmResource = keycloak.realm(realm);
       return realmResource.users().get(id).toRepresentation();
    }

    @Override
    public void deleteUser(String id) {
        RealmResource realmResource = keycloak.realm(realm);

        try (Response deleteResponse = realmResource.users().delete(id)) {
            if (deleteResponse.getStatus()==200){
                log.info("User deleted");
            }
        } catch (Exception e){
            throw new RuntimeException("Couldn't delete user ",e);
        }
    }

    public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) throws CustomError {
        try (Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(domain)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(userClientId)
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
                // Todo: Store sample response messages in message.properties
                throw new CustomError("Invalid credentials", "02", HttpStatus.BAD_REQUEST);
            }
        }
    }
    }
