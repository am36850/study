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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort.Direction;
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
  public FlatFileItemWriter<PersonDto> writer() {
    return new FlatFileItemWriterBuilder<PersonDto>().name("CSVFileWriter")
        .lineAggregator(getLineAggregator()).forceSync(Boolean.TRUE)
        .resource(new FileSystemResource(getOutputFile())).build();
  }

  private String getOutputFile() {
    return outputFolderPath + generateFileName();
  }

  private String generateFileName() {
    return "ExportUsers_" + System.currentTimeMillis() + ".csv";
  }

  private LineAggregator<PersonDto> getLineAggregator() {
    DelimitedLineAggregator<PersonDto> lineAggregator = new DelimitedLineAggregator<>();
    lineAggregator.setDelimiter(",");
    lineAggregator.setFieldExtractor(getFieldExtractor());
    return lineAggregator;
  }

  private FieldExtractor<PersonDto> getFieldExtractor() {
    BeanWrapperFieldExtractor<PersonDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
    fieldExtractor.setNames(new String[] {"firstName", "lastName", "age", "email"});
    return fieldExtractor;
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Person, PersonDto>chunk(10).reader(reader())
        .processor(itemProcessor).writer(writer()).build();
  }

  @Bean
  public Job importUserJob() {
    return jobBuilderFactory.get("ImportUserJob").incrementer(new RunIdIncrementer())
        .listener(durationListener).flow(step1()).end().build();
  }

}
