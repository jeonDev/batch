package com.jh.batch.application.job.retry;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetryTestJobConfig {

    private final JobRepository jobRepository;
    private final Step step;
    private final static String JOB_NAME = "retryTestJob";

    public RetryTestJobConfig(JobRepository jobRepository, Step retryTestStep) {
        this.jobRepository = jobRepository;
        this.step = retryTestStep;
    }

    @Bean
    public Job retryTestJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}
