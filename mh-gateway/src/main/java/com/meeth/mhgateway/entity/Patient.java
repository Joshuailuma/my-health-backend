package com.meeth.mhgateway.entity;

import com.meeth.mhgateway.enums.Gender;
import com.meeth.mhgateway.enums.RegistrationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "patients")
public class Patient {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private RegistrationStatus registrationStatus;
    private String dateOfBirth;
    private String countryCode;
    private String rating;
    private Gender gender;
}
