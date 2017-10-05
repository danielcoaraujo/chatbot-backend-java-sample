package oi.digital.chatbot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 06/09/17.
 */
@Component
public class ActionStrategy {

    @Value("${key.action}")
    private String ACTION_KEY;

    @Autowired
    Map<String, Action> actionStrategies = new HashMap();

    public String executeAction(String actionName, Map<String, Object> context) {
        try {
            return actionStrategies.get(actionName).executeAction(context);

        } finally {
            context.put(ACTION_KEY, null);
        }
    }
}
