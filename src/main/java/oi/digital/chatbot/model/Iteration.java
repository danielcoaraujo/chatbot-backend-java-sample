package oi.digital.chatbot.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by daniel on 18/10/17.
 */
@Data
@Builder
@Entity
@Table(name = "Iteration")
public class Iteration {
    @Id
    @GeneratedValue
    private int id;
    //userMessage or botMessage
    private String type;
    @Column(length = 1000000)
    private String message;
    private Date createdDate;
    @ManyToOne()
    @JoinColumn(name = "chatbotConversationId")
    private ChatbotConversation chatbotConversation;

    @Tolerate
    Iteration(){}
}
