package oi.digital.chatbot.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by daniel on 18/10/17.
 */
@Data
@Builder
@Entity
@Table(name = "ChatbotConversation")
public class ChatbotConversation {
    @Id
    @GeneratedValue
    private int id;
    private String conversationId;
    private String protocolNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatbotConversation")
    private Set<Iteration> iteration;

    @Tolerate
    ChatbotConversation(){}
}
