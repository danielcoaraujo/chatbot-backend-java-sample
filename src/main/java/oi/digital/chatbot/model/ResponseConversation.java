package oi.digital.chatbot.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 20/09/17.
 */
@Data
@Builder
public class ResponseConversation {

    private List<String> botMessages;
    private Map<String, Object> context;

}
