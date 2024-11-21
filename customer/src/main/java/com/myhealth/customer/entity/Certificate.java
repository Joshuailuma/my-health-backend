package com.myhealth.customer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "certificate")
public class Certificate {
    @Id
    private Integer id;
    private String name;
    private String type;
    private String fileUrl;
    private Doctor doctorId;
}
