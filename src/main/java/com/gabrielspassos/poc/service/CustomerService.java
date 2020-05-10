package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.CustomerDTOBuilder;
import com.gabrielspassos.poc.builder.entity.CustomerEntityBuilder;
import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.exception.NotFoundException;
import com.gabrielspassos.poc.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.gabrielspassos.poc.service.ErrorConstants.NOT_FOUND_CUSTOMER_CODE;
import static com.gabrielspassos.poc.service.ErrorConstants.NOT_FOUND_CUSTOMER_MESSAGE;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private CustomerRepository customerRepository;

    public Mono<CustomerDTO> createCustomer(CustomerRequest customerRequest) {
        return getCustomer(customerRequest.getEmail(), customerRequest.getDocument())
                .onErrorResume(NotFoundException.class,
                        exception -> createCustomerEntity(customerRequest).map(CustomerDTOBuilder::build)
                ).doOnSuccess(customerDTO -> logger.info("Created customer {}", customerDTO));
    }

    public Mono<CustomerDTO> getCustomer(String email, String document) {
        logger.info("Searching for customer with email {} and document {}", email, document);
        return customerRepository.findByEmailAndDocument(email, document)
                .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND_CUSTOMER_CODE, NOT_FOUND_CUSTOMER_MESSAGE)))
                .map(CustomerDTOBuilder::build)
                .doOnSuccess(customerDTO -> logger.info("Found customer {}", customerDTO));
    }

    private Mono<CustomerEntity> createCustomerEntity(CustomerRequest customerRequest) {
        return passwordService.encryptPassword(customerRequest.getPassword())
                .map(encryptedPassword -> CustomerEntityBuilder.build(customerRequest, encryptedPassword))
                .flatMap(customerEntity -> customerRepository.save(customerEntity))
                .doOnSuccess(customerEntity -> logger.info("Saved customer {}", customerEntity));
    }
}
