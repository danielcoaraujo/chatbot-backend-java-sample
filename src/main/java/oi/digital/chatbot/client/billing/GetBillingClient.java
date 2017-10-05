package oi.digital.chatbot.client.billing;

import feign.Headers;
import feign.RequestLine;

public interface GetBillingClient {

    @RequestLine("POST /faturas/em-aberto/consultar")
    @Headers("Content-Type: application/json")
    GetBillingResponse getBilling(GetBillingRequest request);

    @RequestLine("POST /faturas/em-aberto/consultar")
    @Headers("Content-Type: application/json")
    GetBillingResponse getBillingWithExtraBilling(GetBillingWithExtraBillingRequest request);
}