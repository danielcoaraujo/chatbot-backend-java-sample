package oi.digital.chatbot.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by daniel on 05/09/17.
 */
@Data
@ConfigurationProperties("configProperties")
public class ConfigProperties {

    private String workspaceId;
    private String conversationUsername;
    private String conversationPassword;
    private String username;
    private String password;
    private String proxy;
    private int port;
    private boolean useProxy;
}
