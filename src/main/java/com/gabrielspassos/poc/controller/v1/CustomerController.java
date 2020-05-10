package com.gabrielspassos.poc.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.controller.v1.response.CustomerResponse;
import com.gabrielspassos.poc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class CustomerController implements BaseVersion {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/customer")
    public Mono<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest)
                .map(customerDTO -> objectMapper.convertValue(customerDTO, CustomerResponse.class));
    }
}
