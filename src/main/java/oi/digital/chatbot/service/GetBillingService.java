package oi.digital.chatbot.service;

import oi.digital.chatbot.client.billing.*;
import oi.digital.chatbot.client.customer.Customer;
import oi.digital.chatbot.client.customer.Produto;
import oi.digital.chatbot.client.customer.ProdutoExtra;
import oi.digital.chatbot.infrastructure.exception.BotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetBillingService {

    @Autowired
    private GetBillingClient getBillingClient;


    public GetBillingResponse getBillingResponse(GetBillingRequest request){
        return getBillingClient.getBilling(request);
    }

    public GetBillingResponse getBillingWithExtraBillingResponse(GetBillingWithExtraBillingRequest request){
        return getBillingClient.getBillingWithExtraBilling(request);
    }

    public List<Billing> getBillings(Customer customer, String terminalTitular) {
        GetBillingResponse response;
        Produto selectedBilling = getSelectedBilling(customer, terminalTitular);

        if(hasExtraBilling(selectedBilling)){
            GetBillingWithExtraBillingRequest request = makeBillingWithExtraBillingRequest(customer, selectedBilling);
            response = getBillingWithExtraBillingResponse(request);

        }else {
            GetBillingRequest request = makeGetBillingRequest(customer, selectedBilling);
            response = getBillingResponse(request);
        }

        return getBillingListWithValue(response);
    }

    private GetBillingRequest makeGetBillingRequest(Customer customer, Produto selectedBilling) {
        String cpf = customer.getCpf();
        String idContaFatura = selectedBilling.getIdContaFatura();
        String crmOrigem = selectedBilling.getCRMOrigem();
        String terminalTitular = selectedBilling.getNrTerminalTitular();

        return GetBillingRequest.builder()
                .cpfCliente(cpf)
                .numeroTerminal(terminalTitular)
                .idContaFatura(idContaFatura)
                .crmOrigem(crmOrigem)
                .build();
    }

    private GetBillingWithExtraBillingRequest makeBillingWithExtraBillingRequest(Customer customer, Produto selectedBilling) {
        String cpf = customer.getCpf();
        String idContaFatura = selectedBilling.getIdContaFatura();
        String crmOrigem = selectedBilling.getCRMOrigem();
        String terminalTitular = selectedBilling.getNrTerminalTitular();

        ProdutoExtra extraBilling = selectedBilling.getFixoFaturaExtra();
        String numeroTerminalFaturaExtra = extraBilling.getNumeroTerminal();
        String idContaFaturaExtra = extraBilling.getIdContaFatura();
        String crmOrigemFaturaExtra = extraBilling.getCRMOrigem();

        return GetBillingWithExtraBillingRequest.builder()
                .cpfCliente(cpf)
                .numeroTerminal(terminalTitular)
                .idContaFatura(idContaFatura)
                .crmOrigem(crmOrigem)
                .numeroTerminalFaturaExtra(numeroTerminalFaturaExtra)
                .idContaFaturaExtra(idContaFaturaExtra)
                .crmOrigemFaturaExtra(crmOrigemFaturaExtra)
                .build();
    }

    private Produto getSelectedBilling(Customer customer, String terminalTitular) {
        Optional<Produto> optionalBilling = customer
                .getProdutos()
                .stream()
                .filter(terminal -> terminal.getNrTerminalTitular().equals(terminalTitular))
                .findFirst();

        if (optionalBilling.isPresent()) {
            return optionalBilling.get();

        } else{
            throw new BotException("Não encontrei esse terminal dentre os que você possui.");
        }
    }

    private boolean hasExtraBilling(Produto selectedBilling) {
        return selectedBilling.getFixoFaturaExtra() != null;
    }

    private List<Billing> getBillingListWithValue(GetBillingResponse response) {
        return response.getFaturas()
                .stream()
                .filter(f -> Math.signum(f.getValor()) != 0)
                .collect(Collectors.toList());
    }
}
