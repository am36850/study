package com.project.batch.springparallelprocessing.repository;

import com.project.batch.springparallelprocessing.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

}
