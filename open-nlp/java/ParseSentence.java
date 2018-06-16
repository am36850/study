package com.project.opennlp;

import static opennlp.tools.cmdline.parser.ParserTool.parseLine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class ParseSentence {

    public Parse[] parseSentence(String sentence) throws IOException {
        InputStream inputStream = new FileInputStream("C:\\personnal\\open-mlp\\train-data\\en-parser-chunking.bin");
        ParserModel model = new ParserModel(inputStream);
        Parser parser = ParserFactory.create(model);
        return parseLine(sentence, parser, 1);
    }
}
