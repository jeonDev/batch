package com.jh.batch.application.job.db;

import com.jh.batch.application.job.db.service.PayService;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DbJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final PayService payService;

    public DbJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, PayService payService) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.payService = payService;
    }
}
