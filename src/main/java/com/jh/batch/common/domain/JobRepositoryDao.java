package com.jh.batch.common.domain;

import org.springframework.batch.core.JobParameters;

public interface JobRepositoryDao {

    Long jobInstanceSave(String jobName, JobParameters jobParameters);

}
