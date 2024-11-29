package com.myhealth.library.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myhealth.library.enums.ROLE;
import com.myhealth.library.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static com.myhealth.library.utils.Messages.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  RegistrationRequest {
    @NotNull(message = FIRST_NAME_REQUIRED)
    @JsonProperty
    private String firstName;

    @NotEmpty(message = LAST_NAME_REQUIRED)
    @JsonProperty
    private String lastName;

    @NotBlank(message = EMAIL_REQUIRED)
    @JsonProperty
    private String emailAddress;

    @NotBlank(message = PHONE_NUMBER_REQUIRED)
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = PASSWORD_REQUIRED)
    private Date dateOfBirth;

    @NotBlank(message = COUNTRY_CODE_REQUIRED)
    private String countryCode;

    @NotBlank(message = GENDER_REQUIRED)
    @JsonProperty
    private Gender gender;

    @JsonProperty
    @NotBlank(message = PASSWORD_REQUIRED)
    private String password;

    @JsonProperty
    @NotBlank(message = ROLE_REQUIRED)
    private ROLE role;
}