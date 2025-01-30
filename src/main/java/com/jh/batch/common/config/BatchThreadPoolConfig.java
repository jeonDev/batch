package com.jh.batch.common.config;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchThreadPoolConfig {

    @Bean
    public TaskExecutor jobTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("jobTaskExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public JobLauncher batchJobLauncher(JobRepository jobRepository, TaskExecutor jobTaskExecutor) throws Exception {
        // Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true...
        TaskExecutorJobLauncher taskExecutorJobLauncher = new TaskExecutorJobLauncher();
        taskExecutorJobLauncher.setJobRepository(jobRepository);
        taskExecutorJobLauncher.setTaskExecutor(jobTaskExecutor);
        taskExecutorJobLauncher.afterPropertiesSet();
        return taskExecutorJobLauncher;
    }
}
