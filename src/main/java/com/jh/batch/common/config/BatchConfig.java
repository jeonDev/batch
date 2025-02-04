package com.jh.batch.common.config;

import com.jh.batch.common.domain.JobRepositoryDao;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {
    private final TaskExecutor jobTaskExecutor;
    private final JobRepositoryDao jobRepositoryDao;

    public BatchConfig(TaskExecutor jobTaskExecutor,
                       JobRepositoryDao jobRepositoryDao) {
        this.jobTaskExecutor = jobTaskExecutor;
        this.jobRepositoryDao = jobRepositoryDao;
    }

    @Override
    public JobLauncher jobLauncher(JobRepository jobRepository) throws BatchConfigurationException {
        TaskExecutorJobLauncher taskExecutorJobLauncher = new TaskExecutorJobLauncher();
        taskExecutorJobLauncher.setJobRepository(jobRepository);
        taskExecutorJobLauncher.setTaskExecutor(jobTaskExecutor);
        try {
            taskExecutorJobLauncher.afterPropertiesSet();
            return taskExecutorJobLauncher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JobRepository jobRepository() throws BatchConfigurationException {
        return new CustomJobRepository(jobRepositoryDao);
    }

}
