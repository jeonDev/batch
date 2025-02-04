package com.jh.batch.common.domain.impl;

import com.jh.batch.common.domain.JobRepositoryDao;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepositoryDaoImpl implements JobRepositoryDao {

    @Override
    public Long jobInstanceSave(String jobName, JobParameters jobParameters) {
        return 1L;
    }
}
