package oi.digital.chatbot.action;

import oi.digital.chatbot.service.GetCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 06/09/17.
 */
@Component("getTerminals")
public class GetTerminalsAction implements Action {

    @Value("${key.cpf}")
    private String CPF_KEY;

    @Autowired
    private GetCustomerService consultarClienteService;

    @Override
    public String executeAction(Map<String, Object> context) {
        String cpf = (String) context.get(CPF_KEY);
        List<String> terminais = consultarClienteService.getMainTerminals(cpf);
        String responseMessage = mountResponseMessage(terminais);
        return responseMessage;
    }

    private String mountResponseMessage(List<String> terminais) {
        StringBuilder sb = new StringBuilder();
        terminais.stream().forEach(t -> {
            sb.append("- " + t);
            sb.append("\n");
        });
        return sb.toString();
    }
}
