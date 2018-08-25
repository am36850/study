package com.project.batch.springbatchdateconversion;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchDateConversionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDateConversionApplication.class, args);
    }
}
