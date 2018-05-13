package com.project.batch.springbatchtasklet.tasklet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MyTasklet implements Tasklet {

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    try {
      Resource resource = new ClassPathResource("test.txt");
      if(resource.getFile().delete()){
        System.out.println("File delete Successful");
      }
      else{
        System.err.println("File delete Un-Successful");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}