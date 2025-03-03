package com.myhealth.customer.repository;

import com.myhealth.customer.entity.Patient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface PatientRepository extends ReactiveCrudRepository<Patient, UUID> {
}
