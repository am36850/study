package com.project.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class PersonNameRecognizer {

	public List<String> getPersonNames(String[] tokenizedSentence) throws IOException {
		InputStream inputStream = new FileInputStream("C:\\personnal\\open-mlp\\train-data\\en-ner-person.bin"); 
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
