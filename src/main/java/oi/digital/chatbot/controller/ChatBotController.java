package oi.digital.chatbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import oi.digital.chatbot.model.RequestConversation;
import oi.digital.chatbot.model.ResponseConversation;
import oi.digital.chatbot.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class ChatBotController {

    @Autowired
    ChatBotService chatBotService;

    @PostMapping(value = "/chatbot",
                 consumes = "application/json",
                 produces = "application/json")
    public ResponseEntity<ResponseConversation> chatbot(@RequestBody RequestConversation request) throws JsonProcessingException {
        ResponseConversation response = chatBotService.msgBot(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

