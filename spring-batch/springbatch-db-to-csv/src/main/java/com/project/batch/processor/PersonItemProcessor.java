package com.project.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.project.batch.dto.PersonDto;
import com.project.batch.model.Person;

@Component("personItemProcessor")
public class PersonItemProcessor implements ItemProcessor<Person, PersonDto> {

  @Override
  public PersonDto process(Person person) throws Exception {
    PersonDto dto = new PersonDto();
    dto.setAge(person.getAge().toString());
    dto.setEmail(person.getEmail());
    dto.setFirstName(person.getFirstName());
    dto.setLastName(person.getLastName());
    return dto;
  }

}
