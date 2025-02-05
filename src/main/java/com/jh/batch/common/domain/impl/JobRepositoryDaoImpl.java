package com.jh.batch.common.domain.impl;

import com.jh.batch.common.domain.JobRepositoryDao;
import com.jh.batch.common.domain.entity.JobInstance;
import com.jh.batch.common.domain.entity.JobInstanceRepository;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepositoryDaoImpl implements JobRepositoryDao {

    private final JobInstanceRepository jobInstanceRepository;

    public JobRepositoryDaoImpl(JobInstanceRepository jobInstanceRepository) {
        this.jobInstanceRepository = jobInstanceRepository;
    }

    @Override
    public JobInstance jobInstanceSave(String jobName, JobParameters jobParameters) {
        JobInstance jobInstance = JobInstance.of(jobName, jobParameters);
        return jobInstanceRepository.save(jobInstance);
    }
}
