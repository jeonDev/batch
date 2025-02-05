package com.jh.batch.common.domain;

import com.jh.batch.common.domain.entity.JobInstance;
import org.springframework.batch.core.JobParameters;

public interface JobRepositoryDao {

    JobInstance jobInstanceSave(String jobName, JobParameters jobParameters);

}
