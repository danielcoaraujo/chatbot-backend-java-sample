package oi.digital.chatbot.service;

import oi.digital.chatbot.action.ActionStrategy;
import oi.digital.chatbot.infrastructure.ConfigProperties;
import oi.digital.chatbot.model.RequestConversation;
import oi.digital.chatbot.model.ResponseConversation;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 01/09/17.
 */
@Service
public class ChatBotService {

    public static final String ACTION_KEY = "action";

    @Autowired
    private ConfigProperties properties;

    @Autowired
    private ConversationService service;

    @Autowired
    private ActionStrategy actionStrategy;


    public ResponseConversation msgBot(RequestConversation requestConversation) {
        MessageRequest request = new MessageRequest.Builder()
                .inputText(requestConversation.getUserMessage())
                .context(requestConversation.getContext())
                .build();

        MessageResponse response = service
                .message(properties.getWorkspaceId(), request)
                .execute();

        Map<String, Object> updatedContext = response.getContext();
        List<String> botMessage = ((List<String>)response.getOutput().get("text"));
        List<String> botResponse =  getBotResponse(botMessage, updatedContext);

        ResponseConversation responseConversation = ResponseConversation
                    .builder()
                    .context(updatedContext)
                    .botMessages(botResponse)
                    .build();
        return responseConversation;
    }

    private List<String> getBotResponse(List<String> botMessage, Map<String, Object> context) {
        List<String> botResponse = new ArrayList<>();
        for (String message : botMessage) {
            botResponse.add(message);
            String action = (String)context.get(ACTION_KEY);
            if(action != null) {
                botResponse.add(actionStrategy.executeAction(action, context));
            }
        }
        return botResponse;
    }
}
