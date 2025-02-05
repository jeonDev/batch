package com.jh.batch.common.config;

import com.jh.batch.common.domain.JobRepositoryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@Component
public class CustomJobRepository implements JobRepository {

    private final JobRepositoryDao jobRepositoryDao;

    public CustomJobRepository(JobRepositoryDao jobRepositoryDao) {
        this.jobRepositoryDao = jobRepositoryDao;
    }

    @Override
    public boolean isJobInstanceExists(String jobName, JobParameters jobParameters) {
        log.info("isJobInstanceExists");
        return false;
    }

    @Override
    public JobInstance createJobInstance(String jobName, JobParameters jobParameters) {
        log.info("createJobInstance : {}", jobName);
        Long l = jobRepositoryDao.jobInstanceSave(jobName, jobParameters);

        return new JobInstance(l, jobName);
    }

    @Override
    public JobExecution createJobExecution(String jobName, JobParameters jobParameters) {
        log.info("createJobExecution : {}", jobName);
        JobInstance jobInstance = this.createJobInstance(jobName, jobParameters);
        JobExecution jobExecution = new JobExecution(jobInstance, jobParameters.getLong(jobName), jobParameters);
        jobExecution.setStartTime(LocalDateTime.now());
        jobExecution.setCreateTime(LocalDateTime.now());
        jobExecution.setId(1L);
        jobExecution.setStatus(BatchStatus.STARTED);
        jobExecution.setVersion(1);
        return jobExecution;
    }

    // 1. AbstractJob -> update
    @Override
    public void update(JobExecution jobExecution) {
        log.info("update Job! : {}", jobExecution.toString());

    }

    @Override
    public void add(StepExecution stepExecution) {
        log.info("add Step!  : {}", stepExecution.toString());
    }

    @Override
    public void addAll(Collection<StepExecution> stepExecutions) {
        log.info("addAll Step!  : {}", stepExecutions.toString());

    }

    @Override
    public void update(StepExecution stepExecution) {
        log.info("update Step!  : {}", stepExecution.toString());
    }

    @Override
    public void updateExecutionContext(StepExecution stepExecution) {
        log.info("updateExecutionContext Step!  : {}", stepExecution.toString());
    }

    @Override
    public void updateExecutionContext(JobExecution jobExecution) {
        log.info("updateExecutionContext Job!  : {}", jobExecution.toString());
    }

    @Override
    public StepExecution getLastStepExecution(JobInstance jobInstance, String stepName) {
        log.info("getLastStepExecution Job!  : {}", jobInstance.toString());
        return null;
    }

    @Override
    public long getStepExecutionCount(JobInstance jobInstance, String stepName) {
        log.info("getStepExecutionCount Job!  : {}", jobInstance.toString());
        return 0;
    }

    @Override
    public JobExecution getLastJobExecution(String jobName, JobParameters jobParameters) {
        log.info("getLastJobExecution Job! :{}", jobName);
        return null;
    }
}
