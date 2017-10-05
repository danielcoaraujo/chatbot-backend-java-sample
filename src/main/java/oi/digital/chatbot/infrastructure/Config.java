package oi.digital.chatbot.infrastructure;

import oi.digital.chatbot.client.billing.GetBillingClient;
import oi.digital.chatbot.client.customer.GetCustomerClient;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import feign.Feign;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class Config {

    @Value("${url.consultarFatura}")
    private String URL_CONSULTAR_FATURA;
    @Value("${url.consultarCliente}")
    private String URL_CONSULTAR_CLIENTE;

    private Proxy proxy;
    private Authenticator proxyAuthenticator;
    private OkHttpClient okHttpClient;
    private ConfigProperties configProperties;

    @Autowired
    public Config(ConfigProperties configurationProperties) {
        this.configProperties = configurationProperties;

        proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(
                    configProperties.getUsername(), configProperties.getPassword());
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };

        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                configProperties.getProxy(), configProperties.getPort()));

        okHttpClient = getHttpClient(configurationProperties.isUseProxy());
    }

    private OkHttpClient getHttpClient(boolean useProxy) {
        if(useProxy){
            return new OkHttpClient.Builder()
                    .proxy(proxy)
                    .proxyAuthenticator(proxyAuthenticator)
                    .build();
        }else{
            return new OkHttpClient.Builder()
                    .build();
        }
    }

    @Bean
    public GetCustomerClient getCustomerClient(){

        return Feign.builder()
                .client(new feign.okhttp.OkHttpClient(okHttpClient))
                .decoder(new GsonDecoder())
                .target(GetCustomerClient.class, URL_CONSULTAR_CLIENTE);
    }

    @Bean
    public GetBillingClient getBillingClient(){

        return Feign.builder()
                .client(new feign.okhttp.OkHttpClient(okHttpClient))
                .decoder(new GsonDecoder())
                .encoder(new FormEncoder(new GsonEncoder()))
                .target(GetBillingClient.class, URL_CONSULTAR_FATURA);
    }

    @Bean
    public ConversationService conversationService(){

        ConversationService conversationService =
                new ConversationService(ConversationService.VERSION_DATE_2016_09_20) {
            @Override
            protected OkHttpClient configureHttpClient() {
                return okHttpClient;
            }
        };
        conversationService.setUsernameAndPassword(
                configProperties.getConversationUsername(),
                configProperties.getConversationPassword());

        return conversationService;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST","GET");
            }
        };
    }

}
