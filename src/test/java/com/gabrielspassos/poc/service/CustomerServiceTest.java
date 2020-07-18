package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import com.gabrielspassos.poc.exception.NotFoundException;
import com.gabrielspassos.poc.repository.CustomerRepository;
import com.gabrielspassos.poc.stub.EntityStub;
import com.gabrielspassos.poc.stub.RequestStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private PasswordService passwordService;
    @Mock
    private CustomerRepository customerRepository;

    private final CustomerRequest customerRequest = RequestStub.create();
    private final CustomerEntity customerEntity = EntityStub.create();
    private final String email = "gabriel@gmail.com";
    private final String encryptedPassword = "xxx";

    @Test
    public void shouldReturnCustomers() {
        given(customerRepository.findAll()).willReturn(Flux.just(customerEntity));

        List<CustomerDTO> customerDTOList = customerService.getCustomers(null).collectList().block();

        assertNotNull(customerDTOList);
        assertEquals(1, customerDTOList.size());
        assertEquals("16535378098", customerDTOList.get(0).getDocument());
    }

    @Test
    public void shouldCreateCustomer() {
        given(customerRepository.findByEmailAndStatus("gabriel@gmail.com", ACTIVE)).willReturn(Mono.empty());
        given(passwordService.encryptPassword("1234")).willReturn(Mono.just(encryptedPassword));
        given(customerRepository.save(any())).willReturn(Mono.just(customerEntity));

        CustomerDTO customerDTO = customerService.createCustomer(customerRequest).block();

        assertNotNull(customerDTO);
        assertEquals("16535378098", customerDTO.getDocument());
    }

    @Test
    public void shouldReturnCustomerByEmail() {
        given(customerRepository.findByEmail(email)).willReturn(Mono.just(customerEntity));

        CustomerDTO customerDTO = customerService.getCustomer(email).block();

        assertNotNull(customerDTO);
        assertEquals("16535378098", customerDTO.getDocument());
    }

    @Test
    public void shouldThrowErrorToGetCustomerByEmail() {
        given(customerRepository.findByEmail(any())).willReturn(Mono.empty());

        Assertions.assertThrows(NotFoundException.class, () -> customerService.getCustomer(email).block());
    }

    @Test
    public void shouldReturnUpdatedCustomer() {
        given(customerRepository.findByEmail(email)).willReturn(Mono.just(customerEntity));
        given(passwordService.encryptPassword("1234")).willReturn(Mono.just(encryptedPassword));
        given(customerRepository.save(any())).willReturn(Mono.just(customerEntity));

        CustomerDTO customerDTO = customerService.updateCustomer(email, customerRequest).block();

        assertNotNull(customerDTO);
        assertEquals("16535378098", customerDTO.getDocument());
    }

    @Test
    public void shouldDeleteCustomer() {
        CustomerEntity deletedEntity = EntityStub.create();
        deletedEntity.setStatus(CustomerStatusEnum.INACTIVE);
        given(customerRepository.findByEmail(email)).willReturn(Mono.just(customerEntity));
        given(customerRepository.save(any())).willReturn(Mono.just(deletedEntity));

        CustomerDTO customerDTO = customerService.deleteCustomer(email).block();

        assertNotNull(customerDTO);
        assertEquals("16535378098", customerDTO.getDocument());
    }
}