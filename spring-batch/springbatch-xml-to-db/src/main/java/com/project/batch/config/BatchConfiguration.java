package com.project.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.project.batch.dto.PersonDto;
import com.project.batch.listener.DurationCaptureListener;
import com.project.batch.model.Person;
import com.project.batch.processor.PersonItemProcessor;
import com.project.batch.repository.PersonRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private DurationCaptureListener durationListener;

	@Autowired
	private PersonItemProcessor itemProcessor;

	@Bean
	public StaxEventItemReader<PersonDto> reader() {
		return new StaxEventItemReaderBuilder<PersonDto>().name("XMLItemReader").unmarshaller(getUnMarshaller())
				.addFragmentRootElements("PersonDto").resource(new ClassPathResource("sample.xml")).build();
	}

	private XStreamMarshaller getUnMarshaller() {
		Map<String, String> aliasesMap = new HashMap<String, String>();
		aliasesMap.put("PersonDto", "com.project.batch.dto.PersonDto");
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliasesMap);
		return marshaller;
	}

	@Bean
	public RepositoryItemWriter<Person> writer() {
		return new RepositoryItemWriterBuilder<Person>().methodName("save").repository(personRepository).build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<PersonDto, Person>chunk(10).reader(reader()).processor(itemProcessor)
				.writer(writer()).build();
	}

	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("ImportUserJob").incrementer(new RunIdIncrementer()).listener(durationListener)
				.flow(step1()).end().build();
	}

}