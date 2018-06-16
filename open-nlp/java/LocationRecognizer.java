package com.project.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class LocationRecognizer {

	public List<String> findLocationsInTokenizedSentence(String[] sentence) throws IOException{
		InputStream inputStreamNameFinder = new FileInputStream("C:\\personnal\\open-mlp\\train-data\\en-ner-location.bin");       
		TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder); 
		NameFinderME nameFinder = new NameFinderME(model);      
		Span nameSpans[] = nameFinder.find(sentence);        
		List<String> location = new ArrayList<>();
		for(Span s : nameSpans) {
			location.add(sentence[s.getStart()]);
		}
		return location;
	}

}
