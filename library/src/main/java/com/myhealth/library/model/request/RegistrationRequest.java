package com.myhealth.library.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myhealth.library.enums.Gender;
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

    @NotBlank(message = "Phone number")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotBlank(message = "Password required")
    private Date dateOfBirth;

    @NotBlank(message = "Country code required")
    private String countryCode;
    @NotBlank(message = "Gender required")
    private Gender gender;

    @JsonProperty
    @NotBlank(message = "Password required")
    private String password;
}
