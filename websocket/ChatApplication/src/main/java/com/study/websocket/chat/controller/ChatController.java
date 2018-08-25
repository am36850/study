package com.study.websocket.chat.controller;

import com.study.websocket.chat.dto.MessageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDto send(Message<MessageDto> message){
        LOGGER.info("request received :{}",message.getPayload());
        MessageDto messageDto = message.getPayload();
        messageDto.setReply("Request Processed successfully");
        LOGGER.info("Sending Response :{}",messageDto);
        return messageDto;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        simpMessagingTemplate.convertAndSend("/topic/messages",getMessageDto());
    }

    private Object getMessageDto() {
        MessageDto messageDto = new MessageDto();
        messageDto.setFrom("Anant");
        return messageDto;
    }
}
