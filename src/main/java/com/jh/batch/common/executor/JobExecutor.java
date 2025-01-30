package com.jh.batch.common.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@Configuration
public class JobExecutor {

    private final JobLauncher jobLauncher;

    public JobExecutor(JobLauncher batchJobLauncher) {
        this.jobLauncher = batchJobLauncher;
    }

    @Async
    public void execute(Job job, JobParameters jobParameters) {
        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("Job Execute Error", e);
        }
    }
}
