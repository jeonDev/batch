package com.jh.batch.common.domain.impl;

import com.jh.batch.common.domain.JobRepositoryDao;
import com.jh.batch.common.domain.entity.JobExecutionEntity;
import com.jh.batch.common.domain.entity.JobExecutionRepository;
import com.jh.batch.common.domain.entity.JobInstanceEntity;
import com.jh.batch.common.domain.entity.JobInstanceRepository;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepositoryDaoImpl implements JobRepositoryDao {

    private final JobInstanceRepository jobInstanceRepository;
    private final JobExecutionRepository jobExecutionRepository;

    public JobRepositoryDaoImpl(JobInstanceRepository jobInstanceRepository,
                                JobExecutionRepository jobExecutionRepository) {
        this.jobInstanceRepository = jobInstanceRepository;
        this.jobExecutionRepository = jobExecutionRepository;
    }

    @Override
    public JobInstanceEntity jobInstanceSave(String jobName, JobParameters jobParameters) {
        JobInstanceEntity jobInstance = JobInstanceEntity.of(jobName, jobParameters);
        return jobInstanceRepository.save(jobInstance);
    }

    @Override
    public JobExecutionEntity jobExecutionSave(Long jobInstanceId) {
        JobExecutionEntity jobExecutionEntity = JobExecutionEntity.of(jobInstanceId);
        return jobExecutionRepository.save(jobExecutionEntity);
    }

    @Override
    public void jobUpdate(JobExecution jobExecution) {
        JobExecutionEntity jobExecutionEntity = jobExecutionRepository.findById(jobExecution.getId())
                .orElseThrow();
        jobExecutionEntity.update(jobExecution);
        jobExecutionRepository.save(jobExecutionEntity);

//        JobInstanceEntity jobInstanceEntity = jobInstanceRepository.findById(jobExecution.getJobId())
//                .orElseThrow();
//        jobInstanceEntity.update(jobExecution);
//        jobInstanceRepository.save(jobInstanceEntity);
    }

}
