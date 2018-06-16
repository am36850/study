package com.project.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class PartsOfSpeechRecognizer {

	public String[] getPartsOfSpeech(String[] tokenizeSentence) throws IOException {
		InputStream inputStream = new FileInputStream("C:/personnal/open-mlp/train-data/en-pos-maxent.bin");
		POSModel model = new POSModel(inputStream);
		POSTaggerME tagger = new POSTaggerME(model);
		return tagger.tag(tokenizeSentence);
	}
}
