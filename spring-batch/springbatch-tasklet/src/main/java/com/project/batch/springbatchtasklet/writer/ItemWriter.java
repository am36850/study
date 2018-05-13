package com.project.batch.springbatchtasklet.writer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ItemWriter implements org.springframework.batch.item.ItemWriter<String> {


  @Override
  public void write(List<? extends String> paths) throws Exception {
    for (String filePath : paths) {
      Resource resource = new ClassPathResource(filePath);
      InputStream inputStream = resource.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
      }
      bufferedReader.close();
    }
  }
}
