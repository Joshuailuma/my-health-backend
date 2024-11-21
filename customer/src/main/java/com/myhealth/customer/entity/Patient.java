package com.myhealth.customer.entity;

import com.myhealth.library.enums.Gender;
import com.myhealth.library.enums.RegistrationStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "patient")
public class Patient {
    @Id
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
