package com.project.batch.springbatchmultiresourcereader.config;

import com.project.batch.springbatchmultiresourcereader.dto.PersonDto;
import com.project.batch.springbatchmultiresourcereader.listener.DurationCaptureListener;
import com.project.batch.springbatchmultiresourcereader.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private DurationCaptureListener durationListener;

  @Autowired
  private PersonItemProcessor itemProcessor;

  @Value(value = "input/person*.csv")
  private Resource[] multiResources;

  @Value("${output.folder.path}")
  private String output_folder;

  @Bean
  FlatFileItemReader<PersonDto> reader() {
    return new FlatFileItemReaderBuilder<PersonDto>().name("PersonFlatFileReader")
        .targetType(PersonDto.class).delimited()
        .delimiter(",").names(new String[]{"firstName", "lastName", "email", "age"}).build();
  }

  @Bean
  MultiResourceItemReader<PersonDto> multiResourceItemReader() {
    MultiResourceItemReader<PersonDto> multiResourceItemReader = new MultiResourceItemReader<>();
    multiResourceItemReader.setResources(multiResources);
    multiResourceItemReader.setDelegate(reader());
    return multiResourceItemReader;
  }

  @Bean
  FlatFileItemWriter<PersonDto> writer() {
    return new FlatFileItemWriterBuilder<PersonDto>().name("multi_resource_item_writer")
        .lineAggregator(getLineAggregator()).resource(externalFileSystemOutputResource()).build();
  }

  private Resource externalFileSystemOutputResource() {
    return new FileSystemResource(output_folder + System.currentTimeMillis() + ".csv");
  }

  private LineAggregator<PersonDto> getLineAggregator() {
    DelimitedLineAggregator<PersonDto> delimitedLineAggregator = new DelimitedLineAggregator<>();
    delimitedLineAggregator.setDelimiter(",");
    BeanWrapperFieldExtractor<PersonDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
    fieldExtractor.setNames(new String[]{"firstName", "lastName", "email", "age"});
    delimitedLineAggregator.setFieldExtractor(fieldExtractor);
    return delimitedLineAggregator;
  }

  @Bean
  Step step1() {
    return stepBuilderFactory.get("step1").<PersonDto, PersonDto>chunk(10)
        .reader(multiResourceItemReader())
        .processor(itemProcessor).writer(writer()).build();
  }

  @Bean
  Job importUserJob() {
    return jobBuilderFactory.get("ImportUserJob").incrementer(new RunIdIncrementer())
        .listener(durationListener).flow(step1()).end().build();
  }

}
