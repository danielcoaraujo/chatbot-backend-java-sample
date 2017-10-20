package oi.digital.chatbot.service;

import oi.digital.chatbot.client.customer.GetCustomerClient;
import oi.digital.chatbot.client.customer.Customer;
import oi.digital.chatbot.client.customer.Produto;
import oi.digital.chatbot.infrastructure.exception.BotException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCustomerService {

    @Value("${crm.oi-tv}")
    private String CRM_OI_TV;

    @Autowired
    private GetCustomerClient getCustomerClient;

    public Customer getCustomer(String cpf){
        try {
            Customer customer = getCustomerClient.getCustomer(cpf);
            if(customer == null){
                throw new BotException("Desculpe, " +
                        "não encontrei esse cpf em nosso sistema, poderia repetir?");
            }
            return customer;

        } catch (FeignException ex){
            throw new BotException("Desculpe, " +
                    "não encontrei esse cpf em nosso sistema, poderia repetir?");
        }
    }

    public List<String> getMainTerminals(String cpf){
        List<Produto> products = getCustomer(cpf).getProdutos();
        List<String> terminals = products
                .stream()
                .filter(t -> !t.getCRMOrigem().equalsIgnoreCase(CRM_OI_TV))
                .map(t -> t.getNrTerminalTitular())
                .collect(Collectors.toList());
        return terminals;
    }
}
