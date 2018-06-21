package com.project.chatbot.service;

import com.project.chatbot.config.Jira;
import com.project.chatbot.opennlp.SentenceDetection;

import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ChatServiceImpl implements ChatService {

    @Autowired
    Chat chat;

    @Autowired
    SentenceDetection sentenceDetection;

    static String createdJiras = new String();

    @Override
    public String getResponse(String paragraph) {

        List<String> parsedSentenced = getParsedSentences(paragraph);
        String response = new String();
        if ((paragraph == null) || (paragraph.length() < 1)) {
            paragraph = MagicStrings.null_input;
        }
        StringBuilder responseBuilder = new StringBuilder("");
        for (String sentence : parsedSentenced) {
            try {
                boolean checkForDifferentAnswer = false;
                if (sentence.toLowerCase().contains("this does not answer my question") || sentence.toLowerCase().contains("does not answer my question")) {
                    sentence = chat.inputHistory.get(0);
                    checkForDifferentAnswer = true;
                }
                response = chat.multisentenceRespond(sentence);
                while(chat.responseHistory.get(0).equalsIgnoreCase(chat.responseHistory.get(1))){
                    response = chat.multisentenceRespond(sentence);
                }
                if (!StringUtils.isEmpty(response) && response.contains("Support ticket created for the issue.")) {
                    response =response + MessageFormat.format("Please refer {0} for further communication.", createdJiraId());
                }
                responseBuilder.append(response);
            }
            catch (Exception e) {
            } while (response.contains("&lt;"))
                response = response.replace("&lt;", "<");
            while (response.contains("&gt;"))
                response = response.replace("&gt;", ">");
            if (!StringUtils.isEmpty(createdJiras) && "What is the status on ticket?".equalsIgnoreCase(sentence)) {
                Jira jira = new Jira();
                String status = jira.checkJiraStatus(createdJiras);
                responseBuilder.append("Ticket " + createdJiras + " is "+status);
            }
        }
        return responseBuilder.toString();
    }

    private static String createdJiraId() {
        createdJiras = "IAPP-190737";
        return createdJiras;
    }

    private List<String> getParsedSentences(String paragraph) {
        try {
            String[] sentences = sentenceDetection.getSentencesFromParagraph(paragraph);
            return Arrays.asList(sentences);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
