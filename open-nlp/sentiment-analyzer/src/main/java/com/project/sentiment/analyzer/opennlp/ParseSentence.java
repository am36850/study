package com.project.sentiment.analyzer.opennlp;

import static opennlp.tools.cmdline.parser.ParserTool.parseLine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

@Component
public class ParseSentence {

    @Value("${open.nlp.train.data.path}")
    private String model_data_path;

    public Parse[] parseSentence(String sentence) throws IOException {
        InputStream inputStream = new FileInputStream(model_data_path+"en-parser-chunking.bin");
        ParserModel model = new ParserModel(inputStream);
        Parser parser = ParserFactory.create(model);
        return parseLine(sentence, parser, 1);
    }
}
