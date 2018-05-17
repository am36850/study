package com.project.batch.springparallelprocessing.config;

import com.project.batch.springparallelprocessing.dto.PersonDto;
import com.project.batch.springparallelprocessing.listener.DurationCaptureListener;
import com.project.batch.springparallelprocessing.model.Person;
import com.project.batch.springparallelprocessing.processor.PersonItemProcessor;
import com.project.batch.springparallelprocessing.repository.PersonRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

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
  SimpleAsyncTaskExecutor getSimpleAsyncTaskExecutor(){
   return new SimpleAsyncTaskExecutor("spring-batch");
  }

  @Bean
  public FlatFileItemReader<PersonDto> reader() {
    return new FlatFileItemReaderBuilder<PersonDto>().name("PersonFlatFileReader")
        .resource(new FileSystemResource("D:\\workspace\\study\\spring-batch\\sample-data\\persons.csv")).targetType(PersonDto.class).delimited()
        .delimiter(",").names(new String[] {"firstName", "lastName", "email", "age"}).build();
  }

  @Bean
  public RepositoryItemWriter<Person> writer() {
    return new RepositoryItemWriterBuilder<Person>().methodName("save").repository(personRepository)
        .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<PersonDto, Person>chunk(10).reader(reader())
        .processor(itemProcessor).writer(writer()).taskExecutor(getSimpleAsyncTaskExecutor()).throttleLimit(Runtime.getRuntime().availableProcessors()).build();
  }

  @Bean
  public Job importUserJob() {
    return jobBuilderFactory.get("ImportUserJob").incrementer(new RunIdIncrementer())
        .listener(durationListener).flow(step1()).end().build();
  }

}
