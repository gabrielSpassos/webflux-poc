package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;

public class RequestStub {

    public static CustomerRequest create() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail("gabriel@gmail.com");
        customerRequest.setDocument("16535378098");
        customerRequest.setName("Gabriel Silva Passos");
        customerRequest.setPassword("1234");
        return customerRequest;
    }
}
