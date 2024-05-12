package com.meeth.mhclient.entity;

import com.meeth.mhclient.enums.Gender;
import com.meeth.mhclient.enums.RegistrationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private RegistrationStatus registrationStatus;
    private Date dateOfBirth;
    private String countryCode;
    private String rating;
    private Gender gender;
}
