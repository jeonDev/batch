package com.jh.batch.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final String filePath = "/Users/jjh/project/file/batch/";

    @Bean
    public Job testJob(Step testStep) {
        return new JobBuilder("testJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(testStep)
                .build();
    }

    @Bean
    public Step testStep() {
        return new StepBuilder("testStep", jobRepository)
                .chunk(1, transactionManager)
                .reader(testReader())
                .processor(new ItemProcessor<Object, Object>() {
                    @Override
                    public Object process(Object item) throws Exception {
                        log.info("222");
                        return null;
                    }
                })
                .writer(new ItemWriter<Object>() {
                    @Override
                    public void write(Chunk<?> chunk) throws Exception {
                        log.info("333");
                    }
                })
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Test> testReader() {

        return new FlatFileItemReaderBuilder<Test>()
                .name("testReader")
                .resource(new FileSystemResource(filePath + "TEST1"))
                .encoding("UTF-8")
                .delimited().delimiter(".")
                .names("a", "b", "c", "d")
                .targetType(Test.class)
                .linesToSkip(0)
                .build();
    }
}
