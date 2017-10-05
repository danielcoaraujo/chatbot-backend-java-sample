package oi.digital.chatbot.infrastructure.exception;

/**
 * Created by daniel on 11/09/17.
 */
public class BotException extends RuntimeException{

    public BotException(String message) {
        super(message);
    }
}
