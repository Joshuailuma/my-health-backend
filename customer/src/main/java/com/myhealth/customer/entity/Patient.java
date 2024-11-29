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
@Table(name = "patient")
public class Patient {
    @Id
    private UUID id;
    private UUID userId;
}
