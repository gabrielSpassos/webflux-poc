package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, Long> {

    Mono<CustomerEntity> findByEmail(String email);
    Mono<CustomerEntity> findByEmailAndStatus(String email, CustomerStatusEnum status);
    Flux<CustomerEntity> findByStatus(CustomerStatusEnum status);
}
