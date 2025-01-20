package com.jh.batch.application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobStarter {

    private final Job basicJob;
    private final Job payloadJob;
    private final Job dbJob;

    public JobStarter(Job basicJob, Job payloadJob, Job dbJob) {
        this.basicJob = basicJob;
        this.payloadJob = payloadJob;
        this.dbJob = dbJob;
    }

    @Bean
    public ApplicationRunner runner(JobLauncher jobLauncher) {
        return args -> {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("dt", "20250120")
                    .toJobParameters();

//            jobLauncher.run(basicJob, jobParameters);
//            jobLauncher.run(payloadJob, jobParameters);
            jobLauncher.run(dbJob, jobParameters);
        };
    }
}
