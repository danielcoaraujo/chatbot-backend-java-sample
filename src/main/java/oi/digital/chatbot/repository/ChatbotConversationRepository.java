package oi.digital.chatbot.repository;

import oi.digital.chatbot.model.ChatbotConversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by daniel on 16/10/17.
 */
@Repository
public interface ChatbotConversationRepository extends CrudRepository<ChatbotConversation, String> {

    ChatbotConversation findByConversationIdAndProtocolNumber(String conversationId, String protocolNumber);
}
