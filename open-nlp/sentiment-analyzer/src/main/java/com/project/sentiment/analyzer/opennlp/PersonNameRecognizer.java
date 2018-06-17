package com.project.sentiment.analyzer.opennlp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

@Component
public class PersonNameRecognizer {

	@Value("${open.nlp.train.data.path}")
	private String model_data_path;

	public List<String> getPersonNames(String[] tokenizedSentence) throws IOException {
		InputStream inputStream = new FileInputStream(model_data_path+"en-ner-person.bin");
		TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
		NameFinderME nameFinder = new NameFinderME(model); 
		Span nameSpans[] = nameFinder.find(tokenizedSentence);
		List<String> names = new ArrayList<>();
		for(Span s: nameSpans) {
			names.add(tokenizedSentence[s.getStart()]);
		}
		return names;
	}

}
