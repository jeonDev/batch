package com.jh.batch.application.job.skip;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkipTestJobConfig {

    private final JobRepository jobRepository;
    private final Step step;
    private final static String JOB_NAME = "skipTestJob";

    public SkipTestJobConfig(JobRepository jobRepository, Step skipTestStep) {
        this.jobRepository = jobRepository;
        this.step = skipTestStep;
    }

    @Bean
    public Job skipTestJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}
