package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, Long> {

    Mono<CustomerEntity> findByEmailAndPassword(String email, String password);
    Mono<CustomerEntity> findByEmailAndDocument(String email, String document);
}
