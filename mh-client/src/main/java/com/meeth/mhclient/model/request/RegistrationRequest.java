package com.meeth.mhclient.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meeth.mhclient.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  RegistrationRequest {
    @NotNull(message = "{Name.required}")
    @JsonProperty
    private String firstName;
    @NotBlank(message = "Last required")
    @JsonProperty
    private String lastName;
    @NotBlank(message = "Email required")
    @JsonProperty
    private String emailAddress;
    @NotBlank(message = "Password required")

    private String phoneNumber;
    private String registrationStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    private String countryCode;
    private Double rating;
    private Gender gender;
    @JsonProperty
    private String password;
}
