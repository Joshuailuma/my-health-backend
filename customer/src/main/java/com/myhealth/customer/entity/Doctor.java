package com.myhealth.customer.entity;

import com.myhealth.library.enums.Gender;
import com.myhealth.library.enums.RegistrationStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor {
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
