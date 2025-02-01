package com.jh.batch.common.config;

import com.jh.batch.common.domain.JobRepositoryDao;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomJobRepository implements JobRepository {

    private final JobRepositoryDao jobRepositoryDao;

    public CustomJobRepository(JobRepositoryDao jobRepositoryDao) {
        this.jobRepositoryDao = jobRepositoryDao;
    }

    @Override
    public boolean isJobInstanceExists(String jobName, JobParameters jobParameters) {
        return false;
    }

    @Override
    public JobInstance createJobInstance(String jobName, JobParameters jobParameters) {
        return null;
    }

    @Override
    public JobExecution createJobExecution(String jobName, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        return new JobExecution(createJobInstance(jobName, jobParameters), 1L, jobParameters);
    }

    @Override
    public void update(JobExecution jobExecution) {

    }

    @Override
    public void add(StepExecution stepExecution) {

    }

    @Override
    public void addAll(Collection<StepExecution> stepExecutions) {

    }

    @Override
    public void update(StepExecution stepExecution) {

    }

    @Override
    public void updateExecutionContext(StepExecution stepExecution) {

    }

    @Override
    public void updateExecutionContext(JobExecution jobExecution) {

    }

    @Override
    public StepExecution getLastStepExecution(JobInstance jobInstance, String stepName) {
        return null;
    }

    @Override
    public long getStepExecutionCount(JobInstance jobInstance, String stepName) {
        return 0;
    }

    @Override
    public JobExecution getLastJobExecution(String jobName, JobParameters jobParameters) {
        return null;
    }
}
