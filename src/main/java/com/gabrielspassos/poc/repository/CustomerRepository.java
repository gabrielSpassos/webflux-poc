package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, Long> {

    Mono<CustomerEntity> findByEmail(String email);
}
