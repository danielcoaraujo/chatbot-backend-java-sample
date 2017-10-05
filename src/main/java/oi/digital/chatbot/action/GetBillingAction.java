package oi.digital.chatbot.action;

import oi.digital.chatbot.client.billing.Billing;
import oi.digital.chatbot.client.customer.Customer;
import oi.digital.chatbot.service.GetBillingService;
import oi.digital.chatbot.service.GetCustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 06/09/17.
 */
@Component("getBilling")
public class GetBillingAction implements Action {

    @Value("${key.terminal}")
    private String TERMINAL_KEY;
    @Value("${key.cpf}")
    private String CPF_KEY;

    @Autowired
    private GetCustomerService getCustomerService;

    @Autowired
    private GetBillingService getBillingService;


    @Override
    public String executeAction(Map<String, Object> context) {
        String cpf = (String) context.get(CPF_KEY);
        String terminal = (String) context.get(TERMINAL_KEY);
        Customer customer = getCustomerService.getCustomer(cpf);
        List<Billing> billings = getBillingService.getBillings(customer, terminal);
        return mountResponseMessage(billings, terminal);
    }

    private String mountResponseMessage(List<Billing> billings, String terminal) {
        if(billings.isEmpty()){
            return "Esse terminal não possui fatura em aberto.";

        }else {
            StringBuilder sb = new StringBuilder();
            sb.append("Terminal: ");
            sb.append(terminal);
            sb.append("\n");

            billings.stream().forEach(billing -> {
                sb.append("\nVencimento: ");
                sb.append(dateFormat(billing.getData()));
                sb.append("\nStatus: ");
                sb.append(billing.getStatus());
                sb.append("\nValor: ");
                sb.append(billing.getValor());
                sb.append("\nCódigo: ");
                sb.append(billing.getCodigoDeBarras());
                sb.append("\n");
            });
            return sb.toString();
        }
    }

    private String dateFormat(String date){
        String newDate = "";

        if(StringUtils.isNotBlank(date)) {
            date = date.replace("-","");
            newDate = date.substring(6,8) + "/" + date.substring(4,6) + "/" + date.substring(0,4);
        }
        return newDate;
    }

}
