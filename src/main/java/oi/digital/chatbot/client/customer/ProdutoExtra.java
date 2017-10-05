package oi.digital.chatbot.client.customer;

import lombok.Data;

import java.util.List;

/**
 * Created by daniel on 04/09/17.
 */
@Data
public class ProdutoExtra {

    private String idContaFatura;
    private String numeroTerminal;
    private String tipoPlanoP;
    private String nrTerminalTitular;
    private String fgInadimplente;
    private String indicOCT;
    private String indicInternet;
    private String tpTerminal;
    private String tpPlano;
    private List<ListaPlano> listaPlanos;
    private String fgFraude;
    private String statusTerminal;
    private String UFTerminal;
    private String CRMOrigem;
    private String CPFParticipante;
}
