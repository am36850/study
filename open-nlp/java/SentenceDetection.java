package com.project.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetection {
	
	public String[] getSentensesFromParagraph(String paragraph) throws IOException {
		//Loading sentence detector model 
		InputStream inputStream = new FileInputStream("C:\\personnal\\open-mlp\\train-data\\en-sent.bin"); 
		SentenceModel model = new SentenceModel(inputStream); 
		//Instantiating the SentenceDetectorME class 
		SentenceDetectorME detector = new SentenceDetectorME(model);  
		//Detecting the sentence
		return detector.sentDetect(paragraph); 
	}
}
