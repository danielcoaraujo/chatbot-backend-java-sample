package oi.digital.chatbot.infrastructure;

import oi.digital.chatbot.infrastructure.exception.BotException;
import oi.digital.chatbot.model.ResponseConversation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 05/09/17.
 */
@Slf4j
@ControllerAdvice
public class ControllerConfigAdvice {

    @ExceptionHandler(BotException.class)
    public ResponseEntity<Map<String, Object>> handlerBotException(BotException ex){
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        ResponseConversation responseConversation = ResponseConversation
                .builder()
                .botMessages(messages)
                .build();
        return new ResponseEntity(responseConversation, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handler(){
        List<String> messages = new ArrayList<>();
        messages.add("Desculpe, houve um erro não identificado");
        ResponseConversation responseConversation = ResponseConversation
                .builder()
                .botMessages(messages)
                .build();
        return new ResponseEntity(responseConversation, HttpStatus.OK);
    }

}
