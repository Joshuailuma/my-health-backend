package com.myhealth.customer.repository;

import com.myhealth.customer.entity.Patient;
import com.myhealth.customer.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    Mono<User> findByEmail(String email);
}