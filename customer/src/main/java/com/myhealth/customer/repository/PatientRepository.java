package com.myhealth.customer.repository;

import com.myhealth.customer.entity.Patient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface PatientRepository extends ReactiveCrudRepository<Patient, Integer> {
    Mono<Patient> findByEmail(String email);
}
