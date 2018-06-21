package com.project.chatbot.config;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfiguration {

    @Value("${aiml.file.path}")
    private String aimlFilePath;

    @Value("${jira.username}")
    private String userName;

    @Value("${jira.password}")
    private String password;

    @Value("S{jira.url}")
    private String jiraUrl;

    @Bean
    public Chat chat() {
        Bot bot = new Bot("super", aimlFilePath);
        bot.writeAIMLIFFiles();
        Chat chatSession = new Chat(bot);
        return chatSession;
    }
}
