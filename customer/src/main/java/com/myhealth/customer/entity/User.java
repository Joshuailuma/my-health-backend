package com.myhealth.customer.entity;

import com.myhealth.library.enums.Gender;
import com.myhealth.library.enums.ROLE;
import com.myhealth.library.enums.RegistrationStatus;
import io.r2dbc.postgresql.codec.Point;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private RegistrationStatus registrationStatus;
    private Date dateOfBirth;
    private String countryCode;
    private String rating;
    private Gender gender;
    private ROLE role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal longitude;
    private BigDecimal latitude;
}
