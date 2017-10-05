package oi.digital.chatbot.client.customer;

import lombok.Data;

import java.util.List;

/**
 * Created by daniel on 04/09/17.
 */
@Data
public class Customer {

    public List<Produto> produtos;
    public List<String> crmsCadastro;
    public String dataNascimento;
    public String cpf;
    public String nome;
}
