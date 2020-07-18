package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.builder.response.CustomerResponseBuilder;
import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.controller.v1.response.CustomerResponse;
import com.gabrielspassos.poc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class CustomerController implements BaseVersion {

    private CustomerService customerService;

    @GetMapping(value = "/customers")
    public Flux<CustomerResponse> getCustomers() {
        return customerService.getCustomers()
                .map(CustomerResponseBuilder::build);
    }

    @PostMapping(value = "/customers")
    public Mono<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest)
                .map(CustomerResponseBuilder::build);
    }

    @GetMapping(value = "/customers/{email}")
    public Mono<CustomerResponse> getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomer(email)
                .map(CustomerResponseBuilder::build);
    }

    @PutMapping(value = "/customers/{email}")
    public Mono<CustomerResponse> updateCustomer(@PathVariable String email,
                                                 @RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.updateCustomer(email, customerRequest)
                .map(CustomerResponseBuilder::build);
    }

    @DeleteMapping(value = "/customers/{email}")
    public Mono<CustomerResponse> deleteCustomer(@PathVariable String email) {
        return customerService.deleteCustomer(email)
                .map(CustomerResponseBuilder::build);
    }
}
