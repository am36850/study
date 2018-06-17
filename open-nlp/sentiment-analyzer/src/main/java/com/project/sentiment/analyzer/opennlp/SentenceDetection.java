package com.project.sentiment.analyzer.opennlp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

@Component
public class SentenceDetection {

    @Value("${open.nlp.train.data.path}")
    private String model_data_path;

    public String[] getSentencesFromParagraph(String paragraph) throws IOException {
        //Loading sentence detector model
        InputStream inputStream = new FileInputStream(model_data_path + "en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        //Instantiating the SentenceDetectorME class
        SentenceDetectorME detector = new SentenceDetectorME(model);
        //Detecting the sentence
        return detector.sentDetect(paragraph);
    }
}
