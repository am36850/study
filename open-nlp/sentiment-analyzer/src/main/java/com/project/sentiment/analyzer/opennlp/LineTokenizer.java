package com.project.sentiment.analyzer.opennlp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@Component
public class LineTokenizer {

	@Value("${open.nlp.train.data.path}")
	private String model_data_path;

	public String[] tokenizeLine(String sentence) throws IOException {
		InputStream inputStream = new FileInputStream(model_data_path+"en-token.bin");
		TokenizerModel tokenModel = new TokenizerModel(inputStream);		
		TokenizerME tokenizer = new TokenizerME(tokenModel); 

		//Tokenizing the given raw text 
		return tokenizer.tokenize(sentence);       
	}
}
