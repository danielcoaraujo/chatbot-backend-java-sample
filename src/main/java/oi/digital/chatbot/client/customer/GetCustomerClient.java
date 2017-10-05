package oi.digital.chatbot.client.customer;

import feign.Param;
import feign.RequestLine;

public interface GetCustomerClient {

        @RequestLine("GET /cliente/{cpf}")
        Customer getCustomer(@Param("cpf") String cpf);
}