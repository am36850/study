package com.project.batch.springbatchscheduling.processor;

import com.project.batch.springbatchscheduling.dto.PersonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

@Component("personItemProcessor")
public class PersonItemProcessor implements ItemProcessor<PersonDto,PersonDto> {

	private static Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);

	private static final String EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/="
			+ "?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|"
			+ "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
			+ "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)"
			+ "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:"
			+ "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	@Override
	public PersonDto process(PersonDto personDto){
		boolean isError = false;
		String email = personDto.getEmail();
		String age = personDto.getAge();
		if (!email.matches(EMAIL_REGEXP)) {
			logger.warn("Invalid email address.Hence rejecting the user." + personDto);
			isError = true;
		}
		try {
			NumberUtils.parseNumber(age, Integer.class);
		} catch (NumberFormatException e) {
			logger.warn("Failed to Parse Age : " + personDto);
			isError = true;
		}
		if (isError) {
			return null;
		} else {
			PersonDto person = new PersonDto();
			person.setAge(age);
			person.setEmail(email);
			person.setFirstName(personDto.getFirstName());
			person.setLastName(personDto.getLastName());
			return person;
		}
	}

}
