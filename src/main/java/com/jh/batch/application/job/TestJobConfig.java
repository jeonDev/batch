package com.jh.batch.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job testJob(Step testStep) {
        return new JobBuilder("testJob", jobRepository)
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
    public ItemReader<String> testReader() {
        log.info("111");
        return new ItemReader<String>() {
            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                log.info("222");
                return null;
            }
        };
    }
}
