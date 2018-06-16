package com.project.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class LineTokenizer {

	public String[] tokenizeLine(String sentence) throws IOException {
		InputStream inputStream = new FileInputStream("C:\\personnal\\open-mlp\\train-data\\en-token.bin"); 
		TokenizerModel tokenModel = new TokenizerModel(inputStream);		
		TokenizerME tokenizer = new TokenizerME(tokenModel); 

		//Tokenizing the given raw text 
		return tokenizer.tokenize(sentence);       
	}
}
