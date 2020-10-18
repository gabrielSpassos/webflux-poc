package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.service.CustomerService;
import com.gabrielspassos.poc.stub.DtoStub;
import com.gabrielspassos.poc.stub.RequestStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.INACTIVE;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private WebTestClient webTestClient;
    private final CustomerRequest customerRequest = RequestStub.create();
    private final CustomerDTO customerDTO = DtoStub.create();
    private final String email = "gabriel@gmail.com";

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient
                .bindToController(new CustomerController(customerService))
                .configureClient()
                .build();
    }

    @Test
    public void shouldReturnCustomers() {
        when(customerService.getCustomers(null)).thenReturn(Flux.just(customerDTO));

        webTestClient.get()
                .uri("/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("sddadshada")
                .jsonPath("$.[0].email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.[0].document").isEqualTo("16535378098")
                .jsonPath("$.[0].name").isEqualTo("Gabriel Silva Passos")
                .jsonPath("$.[0].status").isEqualTo("ACTIVE")
                .jsonPath("$.[0].creationDateTime").exists()
                .jsonPath("$.[0].updateDateTime").exists();
    }

    @Test
    public void shouldReturnCustomersWithInactiveStatus() {
        CustomerDTO customerDTO = DtoStub.create();
        customerDTO.setStatus(INACTIVE);

        when(customerService.getCustomers(INACTIVE)).thenReturn(Flux.just(customerDTO));

        webTestClient.get()
                .uri("/v1/customers?status=INACTIVE")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("sddadshada")
                .jsonPath("$.[0].email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.[0].document").isEqualTo("16535378098")
                .jsonPath("$.[0].name").isEqualTo("Gabriel Silva Passos")
                .jsonPath("$.[0].status").isEqualTo("INACTIVE")
                .jsonPath("$.[0].creationDateTime").exists()
                .jsonPath("$.[0].updateDateTime").exists();
    }

    @Test
    public void shouldCreateCustomer() {
        when(customerService.createCustomer(customerRequest)).thenReturn(Mono.just(customerDTO));

        webTestClient.post()
                .uri("/v1/customers")
                .bodyValue(customerRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("sddadshada")
                .jsonPath("$.email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.document").isEqualTo("16535378098")
                .jsonPath("$.name").isEqualTo("Gabriel Silva Passos")
                .jsonPath("$.status").isEqualTo("ACTIVE")
                .jsonPath("$.creationDateTime").exists()
                .jsonPath("$.updateDateTime").exists();
    }

    @Test
    public void shouldReturnCustomerByEmail() {
        when(customerService.getCustomer(email)).thenReturn(Mono.just(customerDTO));

        webTestClient.get()
                .uri("/v1/customers/{email}", email)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("sddadshada")
                .jsonPath("$.email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.document").isEqualTo("16535378098")
                .jsonPath("$.name").isEqualTo("Gabriel Silva Passos")
                .jsonPath("$.status").isEqualTo("ACTIVE")
                .jsonPath("$.creationDateTime").exists()
                .jsonPath("$.updateDateTime").exists();
    }

    @Test
    public void shouldUpdateCustomer() {
        when(customerService.updateCustomer(email, customerRequest)).thenReturn(Mono.just(customerDTO));

        webTestClient.put()
                .uri("/v1/customers/{email}", email)
                .bodyValue(customerRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("sddadshada")
                .jsonPath("$.email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.document").isEqualTo("16535378098")
                .jsonPath("$.name").isEqualTo("Gabriel Silva Passos")
                .jsonPath("$.status").isEqualTo("ACTIVE")
                .jsonPath("$.creationDateTime").exists()
                .jsonPath("$.updateDateTime").exists();
    }

    @Test
    public void shouldDeleteCustomer() {
        CustomerDTO customerDTO = DtoStub.create();
        customerDTO.setStatus(INACTIVE);

        when(customerService.deleteCustomer(email)).thenReturn(Mono.just(customerDTO));

        webTestClient.delete()
                .uri("/v1/customers/{email}", email)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("sddadshada")
                .jsonPath("$.email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.document").isEqualTo("16535378098")
                .jsonPath("$.name").isEqualTo("Gabriel Silva Passos")
                .jsonPath("$.status").isEqualTo("INACTIVE")
                .jsonPath("$.creationDateTime").exists()
                .jsonPath("$.updateDateTime").exists();
    }

}