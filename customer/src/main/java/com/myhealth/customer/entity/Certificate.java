package com.myhealth.customer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "certificate")
public class Certificate {
    @Id
    private UUID id;
    private String name;
    private String type;
    private String fileUrl;
    private UUID doctorId;
}
