package com.project.chatbot.opennlp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

@Component
public class PartsOfSpeechRecognizer {

	@Value("${open.nlp.train.data.path}")
	private String model_data_path;

	public String[] getPartsOfSpeech(String[] tokenizeSentence) throws IOException {
		InputStream inputStream = new FileInputStream(model_data_path+"en-pos-maxent.bin");
		POSModel model = new POSModel(inputStream);
		POSTaggerME tagger = new POSTaggerME(model);
		return tagger.tag(tokenizeSentence);
	}
}
