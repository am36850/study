package com.project.sentiment.analyzer.repository;

import com.project.sentiment.analyzer.domain.EnglishDictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishDictionaryRepository extends JpaRepository<EnglishDictionary, Integer> {

    @Query("select e from EnglishDictionary e where lower(e.word) = :word ")
    EnglishDictionary findByWordLowerCase(@Param("word") String word);
}
