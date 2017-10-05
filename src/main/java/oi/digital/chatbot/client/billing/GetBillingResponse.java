package oi.digital.chatbot.client.billing;

import lombok.Data;

import java.util.List;

/**
 * Created by daniel on 04/09/17.
 */
@Data
public class GetBillingResponse {

    private List<Billing> faturas;
}
