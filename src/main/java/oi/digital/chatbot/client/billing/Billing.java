package oi.digital.chatbot.client.billing;

import lombok.Data;

/**
 * Created by daniel on 04/09/17.
 */
@Data
public class Billing {

    private String data;
    private Float valor;
    private Boolean dacc;
    private String codigoDeBarras;
    private String status;
    private Boolean tem14Meses;
}
