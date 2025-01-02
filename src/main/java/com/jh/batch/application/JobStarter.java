package com.jh.batch.application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobStarter {

    private final Job basicJob;
    private final Job payloadJob;

    public JobStarter(Job basicJob, Job payloadJob) {
        this.basicJob = basicJob;
        this.payloadJob = payloadJob;
    }

    @Bean
    public ApplicationRunner runner(JobLauncher jobLauncher) {
        return args -> {
            JobParameters jobParameters = new JobParameters();
            jobLauncher.run(basicJob, jobParameters);
            jobLauncher.run(payloadJob, jobParameters);
        };
    }
}
