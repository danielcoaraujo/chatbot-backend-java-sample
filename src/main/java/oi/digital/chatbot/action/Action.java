package oi.digital.chatbot.action;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by daniel on 06/09/17.
 */
@Component
public interface Action {

    String executeAction(Map<String, Object> context);
}
