package oi.digital.chatbot.client.billing;

import lombok.Builder;
import lombok.Data;

/**
 * Created by daniel on 04/09/17.
 */
@Data
@Builder
public class GetBillingWithExtraBillingRequest {

    private String cpfCliente;
    private String numeroTerminal;
    private String idContaFatura;
    private String crmOrigem;
    private String numeroTerminalFaturaExtra;
    private String idContaFaturaExtra;
    private String crmOrigemFaturaExtra;
}
