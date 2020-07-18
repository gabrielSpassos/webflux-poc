package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.CustomerDTOBuilder;
import com.gabrielspassos.poc.builder.entity.CustomerEntityBuilder;
import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import com.gabrielspassos.poc.exception.NotFoundException;
import com.gabrielspassos.poc.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.gabrielspassos.poc.config.ErrorConstants.NOT_FOUND_CUSTOMER_CODE;
import static com.gabrielspassos.poc.config.ErrorConstants.NOT_FOUND_CUSTOMER_MESSAGE;
import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.ACTIVE;
import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.INACTIVE;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private PasswordService passwordService;
    private CustomerRepository customerRepository;

    public Flux<CustomerDTO> getCustomers(CustomerStatusEnum status) {
        if (Objects.nonNull(status)) {
            log.info("Searching for customers with status {}", status);
            return customerRepository.findByStatus(status)
                    .map(CustomerDTOBuilder::build);
        }

        log.info("Searching for customers");
        return customerRepository.findAll()
                .map(CustomerDTOBuilder::build);
    }

    public Mono<CustomerDTO> createCustomer(CustomerRequest customerRequest) {
        return customerRepository.findByEmailAndStatus(customerRequest.getEmail(), ACTIVE)
                .switchIfEmpty(Mono.error(IllegalStateException::new))
                .map(CustomerDTOBuilder::build)
                .onErrorResume(IllegalStateException.class, e -> saveEntity(customerRequest))
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
                .map(customerDTO -> CustomerEntityBuilder.build(customerDTO, INACTIVE))
                .flatMap(customerEntity -> customerRepository.save(customerEntity))
                .map(CustomerDTOBuilder::build)
                .doOnSuccess(customerDTO -> log.info("Deleted customer {}", customerDTO));
    }

    private Mono<CustomerDTO> saveEntity(CustomerRequest customerRequest) {
        return passwordService.encryptPassword(customerRequest.getPassword())
                .map(encryptedPassword -> CustomerEntityBuilder.build(customerRequest, encryptedPassword))
                .flatMap(customerEntity -> customerRepository.save(customerEntity))
                .map(CustomerDTOBuilder::build)
                .doOnSuccess(customerDTO -> log.info("Saved customer {}", customerDTO));
    }
}
