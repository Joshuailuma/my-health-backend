package com.myhealth.customer.repository;

import com.myhealth.customer.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    @Query("SELECT * FROM users WHERE email = :email")
    Mono<User> findByEmail(String email);
}