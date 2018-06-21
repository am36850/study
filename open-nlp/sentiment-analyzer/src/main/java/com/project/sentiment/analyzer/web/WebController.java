package com.project.sentiment.analyzer.web;

import com.project.sentiment.analyzer.opennlp.SentenceDetection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class WebController {

    @Autowired
    SentenceDetection sentenceDetection;

    @RequestMapping(value = "/sentencedetection", method = RequestMethod.GET)
    public List<String> parseSentence(@RequestParam("sentence") String paragraph) {
        try {
            String[] muStrings = sentenceDetection.getSentencesFromParagraph(paragraph);
            return Arrays.asList(muStrings);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return Arrays.asList(new String[] { paragraph });
    }
}
