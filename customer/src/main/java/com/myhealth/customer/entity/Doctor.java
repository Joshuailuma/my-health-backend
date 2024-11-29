package com.myhealth.customer.entity;

import com.myhealth.library.enums.Gender;
import com.myhealth.library.enums.RegistrationStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor {
    @Id
    private UUID id;
    private UUID userId;
    private String certificateId;
    private String specialization;
}
