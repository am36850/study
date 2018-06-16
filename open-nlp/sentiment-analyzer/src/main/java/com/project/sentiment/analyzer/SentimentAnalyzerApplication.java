package com.project.sentiment.analyzer;

import com.project.sentiment.analyzer.domain.EnglishDictionary;
import com.project.sentiment.analyzer.repository.EnglishDictionaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Scanner;

@SpringBootApplication
public class SentimentAnalyzerApplication implements CommandLineRunner {

    @Autowired
    EnglishDictionaryRepository englishDictionaryRepository;

    public static void main(String[] args) {
        SpringApplication.run(SentimentAnalyzerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String fileToParse = "C:\\personnal\\study\\open-nlp\\reviews\\SentiWordNet_3.0.0_20130122.txt";
        File file = new File(fileToParse);
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (StringUtils.isEmpty(line) || line.trim().length() == 1 || line.contentEquals("{") || line.contentEquals("}")) {
                    continue;
                }
                String[] tokens = line.split("\t");
                Double positiveRank = 0.0;
                Double negativeRank = 0.0;
                try {
                    positiveRank = Double.parseDouble(tokens[2]);
                    negativeRank = Double.parseDouble(tokens[3]);
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                String wordFromFile = removeUnnecessaryCharacter(tokens[4]);
                String meaning = removeUnnecessaryCharacter(tokens[5]);
                saveRankingForWords(wordFromFile, meaning, positiveRank, negativeRank);
            }
        }

    }
    
    private void saveRankingForWords(String wordFromFile, String meaning, Double positiveRank, Double negativeRank) {
        EnglishDictionary englishDictionary = englishDictionaryRepository.findByWordLowerCase(wordFromFile.toLowerCase());
        if (positiveRank != 0 || negativeRank != 0) {
            if (englishDictionary == null) {
                englishDictionary = new EnglishDictionary();
                englishDictionary.setWord(wordFromFile);
                englishDictionary.setMeaning(meaning);
                englishDictionary.setNegativeRank(negativeRank);
                englishDictionary.setPositiveRank(positiveRank);
            }
            else {
                englishDictionary.setNegativeRank(negativeRank);
                englishDictionary.setPositiveRank(positiveRank);
            }
        }
        if (englishDictionary != null) {
            englishDictionaryRepository.saveAndFlush(englishDictionary);
        }
    }

    private void createDictionaryObjectAndPersist(String word, String meaning) {
        EnglishDictionary englishDictionary = englishDictionaryRepository.findByWordLowerCase(word.toLowerCase());
        if (englishDictionary == null || englishDictionary.getWord() == null) {
            englishDictionary = new EnglishDictionary();
            englishDictionary.setWord(word);
            englishDictionary.setMeaning(meaning);
            englishDictionaryRepository.saveAndFlush(englishDictionary);
        }
    }

    private String removeUnnecessaryCharacter(String token) {
        token = token.trim();
        token = token.replace("\"", "");
        token = token.replace(",", "");
        if (token.contains("#"))
            token = token.substring(0, token.indexOf('#'));
        return token;
    }
}
