package com.project.chatbot.web;

import com.project.chatbot.dto.Example;
import com.project.chatbot.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    ChatService chatService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET,value = "/conversation")
    public ResponseDto getResponse(@RequestParam(value = "sentence",required = false) String sentence){
           String response = chatService.getResponse(sentence);
           ResponseDto dto = new ResponseDto();
           dto.setResponse(response);
           return dto;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST,value = "/conversation2")
    public com.project.chatbot.dto.response.Example getResponse2(@RequestBody Example sentence){
        String response = chatService.getResponse(sentence.getQueryResult().getFulfillmentText());
        com.project.chatbot.dto.response.Example example = new com.project.chatbot.dto.response.Example();
        example.setFulfillmentText(response);
        return example;
    }
}
