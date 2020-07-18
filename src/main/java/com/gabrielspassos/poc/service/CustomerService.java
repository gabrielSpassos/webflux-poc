package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.CustomerDTOBuilder;
import com.gabrielspassos.poc.builder.entity.CustomerEntityBuilder;
import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.exception.NotFoundException;
import com.gabrielspassos.poc.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.gabrielspassos.poc.config.ErrorConstants.NOT_FOUND_CUSTOMER_CODE;
import static com.gabrielspassos.poc.config.ErrorConstants.NOT_FOUND_CUSTOMER_MESSAGE;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private PasswordService passwordService;
    private CustomerRepository customerRepository;

    public Flux<CustomerDTO> getCustomers() {
        log.info("Searching for customers");
        return customerRepository.findAll()
                .map(CustomerDTOBuilder::build);
    }

    public Mono<CustomerDTO> createCustomer(CustomerRequest customerRequest) {
        return getCustomer(customerRequest.getEmail())
                .onErrorResume(NotFoundException.class, e -> saveEntity(customerRequest))
                .doOnSuccess(customerDTO -> log.info("Created customer {}", customerDTO));
    }

    public Mono<CustomerDTO> getCustomer(String email) {
        log.info("Searching for customer with email {}", email);
        return customerRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND_CUSTOMER_CODE, NOT_FOUND_CUSTOMER_MESSAGE)))
                .map(CustomerDTOBuilder::build)
                .doOnSuccess(customerDTO -> log.info("Found customer {}", customerDTO));
    }

    public Mono<CustomerDTO> updateCustomer(String email, CustomerRequest customerRequest) {
        return getCustomer(email)
                .flatMap(customerDTO -> {
                    return passwordService.encryptPassword(customerRequest.getPassword())
                            .map(encryptedPassword -> CustomerEntityBuilder
                                    .build(customerRequest, encryptedPassword, customerDTO)
                            );
                }).flatMap(customerEntity -> customerRepository.save(customerEntity))
                .map(CustomerDTOBuilder::build)
                .doOnSuccess(customerDTO -> log.info("Updated customer {}", customerDTO));

    }

    public Mono<CustomerDTO> deleteCustomer(String email) {
        return getCustomer(email)
                .map(CustomerEntityBuilder::build)
                .flatMap(customerEntity -> {
                    return customerRepository.delete(customerEntity)
                            .map(aVoid -> CustomerDTOBuilder.build(customerEntity));
                });
    }

    private Mono<CustomerDTO> saveEntity(CustomerRequest customerRequest) {
        return passwordService.encryptPassword(customerRequest.getPassword())
                .map(encryptedPassword -> CustomerEntityBuilder.build(customerRequest, encryptedPassword))
                .flatMap(customerEntity -> customerRepository.save(customerEntity))
                .map(CustomerDTOBuilder::build)
                .doOnSuccess(customerDTO -> log.info("Saved customer {}", customerDTO));
    }
}
