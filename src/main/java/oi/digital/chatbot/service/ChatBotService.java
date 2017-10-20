package oi.digital.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import oi.digital.chatbot.action.ActionStrategy;
import oi.digital.chatbot.infrastructure.ConfigProperties;
import oi.digital.chatbot.model.ChatbotConversation;
import oi.digital.chatbot.model.Iteration;
import oi.digital.chatbot.model.RequestConversation;
import oi.digital.chatbot.model.ResponseConversation;
import oi.digital.chatbot.repository.ChatbotConversationRepository;
import oi.digital.chatbot.repository.IterationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by daniel on 01/09/17.
 */
@Service
@Slf4j
public class ChatBotService {

    private static final String ACTION_KEY = "action";
    private static final String PROTOCOL_NUMBER = "protocolNumber";
    private static final String CONVERSATION_ID = "conversation_id";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ConfigProperties properties;

    @Autowired
    private ConversationService service;

    @Autowired
    private ChatbotConversationRepository conversationRepository;

    @Autowired
    private IterationRepository iterationRepository;

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
        List<String> botResponse = getBotResponse(botMessage, updatedContext);

        ResponseConversation responseConversation = ResponseConversation
                .builder()
                .context(updatedContext)
                .botMessages(botResponse)
                .build();

        saveConversation(requestConversation, responseConversation);
        return responseConversation;
    }

    private List<String> getBotResponse(List<String> botMessage, Map<String, Object> context) {
        List<String> botResponse = new ArrayList<>();
        addProtocolNumber(context, botResponse);
        for (String message : botMessage) {
            botResponse.add(message);
            String action = (String)context.get(ACTION_KEY);
            if(action != null) {
                botResponse.add(actionStrategy.executeAction(action, context));
            }
        }
        return botResponse;
    }

    private void addProtocolNumber(Map<String, Object> context, List<String> botResponse) {
        if(context.get(PROTOCOL_NUMBER) == null){
            String protocolNumber = protocolGenerate();
            context.put(PROTOCOL_NUMBER, protocolNumber);
            botResponse.add("O seguinte protocolo foi gerado: " + protocolNumber);
        }
    }

    private String protocolGenerate() {
        Integer randomNum = ThreadLocalRandom.current().
                nextInt(10000000, 99999999 + 1);
        final LocalDateTime now = LocalDateTime.now();

        StringBuilder sb = new StringBuilder();
        sb.append(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now))
                .append(":")
                .append(randomNum.toString());

        return sb.toString();
    }

    private void saveConversation(RequestConversation requestConversation, ResponseConversation responseConversation) {
        String protocolNumber = (String) responseConversation.getContext().get(PROTOCOL_NUMBER);
        String conversationId = (String) responseConversation.getContext().get(CONVERSATION_ID);
        ChatbotConversation chatbotConversation =
                conversationRepository.findByConversationIdAndProtocolNumber
                        (conversationId, protocolNumber);

        if (chatbotConversation == null) {
            ChatbotConversation conversation = ChatbotConversation.builder()
                    .conversationId(conversationId)
                    .protocolNumber(protocolNumber)
                    .build();
            chatbotConversation = conversationRepository.save(conversation);
        }

        for (String message : responseConversation.getBotMessages()) {
            Iteration botIteration = Iteration.builder()
                    .type("botMessage")
                    .message(message)
                    .chatbotConversation(chatbotConversation)
                    .createdDate(new Date())
                    .build();
            iterationRepository.save(botIteration);
//                log.debug(objectMapper.writeValueAsString(botIteration));
        }

        Iteration userIteration = Iteration.builder()
                .type("userMessage")
                .message(requestConversation.getUserMessage())
                .chatbotConversation(chatbotConversation)
                .createdDate(new Date())
                .build();
        iterationRepository.save(userIteration);
//            log.debug(objectMapper.writeValueAsString(userIteration));

    }
}
