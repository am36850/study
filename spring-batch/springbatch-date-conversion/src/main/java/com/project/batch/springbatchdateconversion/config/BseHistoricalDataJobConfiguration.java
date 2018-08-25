package com.project.batch.springbatchdateconversion.config;

import com.project.batch.springbatchdateconversion.domain.BseHistoricalData;
import com.project.batch.springbatchdateconversion.repository.BseHistoricalDataRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class BseHistoricalDataJobConfiguration {

    static final int NUMBER_OF_CORES = 10;

    static final String DEFAULT_DELIMITER = ",";

    final JobBuilderFactory jobBuilderFactory;

    final StepBuilderFactory stepBuilderFactory;

    final BseHistoricalDataRepository bseHistoricalDataRepository;

    @Value("sample_data.csv")
    Resource resource;

    @Bean
    SimpleAsyncTaskExecutor getSimpleAsyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor("spring-batch");
    }

    @Autowired
    public BseHistoricalDataJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, BseHistoricalDataRepository bseHistoricalDataRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.bseHistoricalDataRepository = bseHistoricalDataRepository;
    }

    @Bean
    public FlatFileItemReader<BseHistoricalData> reader() {
        //@formatter:off
        return new FlatFileItemReaderBuilder<BseHistoricalData>()
                   .name("BseHistoricalDataReader")
                   .resource(resource)
                   .linesToSkip(1)
                   .targetType(BseHistoricalData.class)
                   .delimited()
                   .delimiter(DEFAULT_DELIMITER)
                   .names(new String[] { "scCode","scName","scGroup","scType","open","high",
                                         "low","close","last","prevclose","noTrades","noOfShrs",
                                         "netTurnov","tdcloindi","isinCode","tradingDate","filer1","filer2" })
                   .build();
        //@formatter:on
    }

    @Bean
    public RepositoryItemWriter<BseHistoricalData> writer() {
        //@formatter:off
        return new RepositoryItemWriterBuilder<BseHistoricalData>()
                    .methodName("save")
                    .repository(bseHistoricalDataRepository)
                    .build();
        //@formatter:on
    }

    @Bean
    public Step step1() {
        //@formatter:off
        return stepBuilderFactory.get("step1")
               .<BseHistoricalData, BseHistoricalData>chunk(100)
               .reader(reader())
               .writer(writer())
               .taskExecutor(getSimpleAsyncTaskExecutor())
               .throttleLimit(NUMBER_OF_CORES)
               .build();
        //@formatter:on
    }

    @Bean
    public Job importUserJob() {
        //@formatter:off
        return jobBuilderFactory.get("ImportUserJob")
                                .incrementer(new RunIdIncrementer())
                                .flow(step1())
                                .end()
                                .build();
        //@formatter:on
    }

}
