package com.project.chatbot;

import com.project.chatbot.service.ChatService;

import org.alicebot.ab.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatBotApplication {

    @Autowired
    ChatService chatService;

    public static void main(String[] args) {
        SpringApplication.run(ChatBotApplication.class, args);
    }

    /*
    @Override
    public void run(String... args) {
        String textLine = "";
        while (true) {
            System.out.print("Human : ");
            textLine = IOUtils.readInputTextLine();
            String response = chatService.getResponse(textLine);
            System.out.println("Robot : " + response);
        }
    }*/
}
