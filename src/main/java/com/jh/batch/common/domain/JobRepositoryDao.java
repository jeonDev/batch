package com.jh.batch.common.domain;

import com.jh.batch.common.domain.entity.JobExecutionEntity;
import com.jh.batch.common.domain.entity.JobInstanceEntity;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;

public interface JobRepositoryDao {

    JobInstanceEntity jobInstanceSave(String jobName, JobParameters jobParameters);
    JobExecutionEntity jobExecutionSave(Long jobInstanceId);
    void jobUpdate(JobExecution jobExecution);

}
