package oi.digital.chatbot.client.billing;

import lombok.Builder;
import lombok.Data;

/**
 * Created by daniel on 04/09/17.
 */
@Data
@Builder
public class GetBillingRequest {

    private String cpfCliente;
    private String numeroTerminal;
    private String idContaFatura;
    private String crmOrigem;
}
