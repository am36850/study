package com.study.websocket.chat.controller;

import com.study.websocket.chat.dto.MessageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDto send(Message<MessageDto> message){
        LOGGER.info("request received :{}",message.getPayload());
        MessageDto messageDto = message.getPayload();
        messageDto.setReply("Request Processed successfully");
        LOGGER.info("Sending Response :{}",messageDto);
        return messageDto;
    }
}
