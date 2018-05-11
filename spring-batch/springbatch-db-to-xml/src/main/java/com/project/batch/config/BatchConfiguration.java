package com.project.batch.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort.Direction;
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

  @Value("${output.folder.path}")
  String outputFolderPath;

  @Autowired
  ApplicationContext context;

  @Bean
  public RepositoryItemReader<Person> reader() {
    return new RepositoryItemReaderBuilder<Person>().name("PersonItemReader")
        .repository(personRepository).methodName("findAll").sorts(getSortOrder()).build();

  }

  private Map<String, Direction> getSortOrder() {
    Map<String, Direction> sortOrder = new HashMap<>();
    sortOrder.put("personId", Direction.ASC);
    return sortOrder;
  }

  @Bean
  public StaxEventItemWriter<PersonDto> writer() {
    return new StaxEventItemWriterBuilder<PersonDto>().name("PersonItemWriter")
        .overwriteOutput(false).rootTagName("persons").marshaller(getMarshaller())
        .resource(new FileSystemResource(getOutputFile())).build();
  }

  private XStreamMarshaller getMarshaller() {
    Map<String,String> aliasesMap =new HashMap<String,String>();
    aliasesMap.put("com.project.batch.dto.PersonDto", "com.project.batch.dto.PersonDto");
    XStreamMarshaller marshaller = new XStreamMarshaller();
    marshaller.setAliases(aliasesMap);
    return marshaller;   
  }

  private String getOutputFile() {
    return outputFolderPath + generateFileName();
  }

  private String generateFileName() {
    return "ExportUsers_" + System.currentTimeMillis() + ".xml";
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Person, PersonDto>chunk(10).reader(reader())
        .processor(itemProcessor).writer(writer()).build();
  }

  @Bean
  public Job importUserJob() {
    return jobBuilderFactory.get("ImportUserJob").incrementer(new RunIdIncrementer()).preventRestart()
        .listener(durationListener).flow(step1()).end().build();
  }

}
