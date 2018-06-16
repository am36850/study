package com.project.sentiment.analyzer.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "english_dictionary", catalog = "sentiment_analyzer")
public class EnglishDictionary implements java.io.Serializable {

	private Integer id;
	private int version;
	private String word;
	private String meaning;
	private Double positiveRank;
	private Double negativeRank;
	private String synonym;
	private String antonym;

	public EnglishDictionary() {
	}

	public EnglishDictionary(String word, String meaning) {
		this.word = word;
		this.meaning = meaning;
	}

	public EnglishDictionary(String word, String meaning, Double positiveRank, Double negativeRank, String synonym,
			String antonym) {
		this.word = word;
		this.meaning = meaning;
		this.positiveRank = positiveRank;
		this.negativeRank = negativeRank;
		this.synonym = synonym;
		this.antonym = antonym;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Version
	@Column(name = "version", nullable = false)
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "word", nullable = false, length = 200)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "meaning", nullable = false)
	public String getMeaning() {
		return this.meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	@Column(name = "positive_rank", precision = 10, scale = 0)
	public Double getPositiveRank() {
		return this.positiveRank;
	}

	public void setPositiveRank(Double positiveRank) {
		this.positiveRank = positiveRank;
	}

	@Column(name = "negative_rank", precision = 10, scale = 0)
	public Double getNegativeRank() {
		return this.negativeRank;
	}

	public void setNegativeRank(Double negativeRank) {
		this.negativeRank = negativeRank;
	}

	@Column(name = "synonym", length = 1000)
	public String getSynonym() {
		return this.synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	@Column(name = "antonym", length = 1000)
	public String getAntonym() {
		return this.antonym;
	}

	public void setAntonym(String antonym) {
		this.antonym = antonym;
	}

}
