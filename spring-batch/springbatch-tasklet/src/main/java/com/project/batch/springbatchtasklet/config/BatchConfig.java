package com.project.batch.springbatchtasklet.config;

import com.project.batch.springbatchtasklet.listener.DurationCaptureListener;
import com.project.batch.springbatchtasklet.reader.ItemReader;
import com.project.batch.springbatchtasklet.writer.ItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  Tasklet taskletStep;

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Autowired
  private DurationCaptureListener captureListener;


  @Bean
  public Job job() {
    return jobBuilderFactory.get("job")
        .incrementer(new RunIdIncrementer())
        .start(step1()).next(step2()).listener(captureListener)
        .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
        .<String, String>chunk(1)
        .reader(new ItemReader())
        .writer(new ItemWriter())

        .build();
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2")
        .tasklet(taskletStep)
        .build();
  }
}