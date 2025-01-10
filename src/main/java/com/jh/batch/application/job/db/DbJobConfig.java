package com.jh.batch.application.job.db;

import com.jh.batch.application.job.db.domain.entity.Pay;
import com.jh.batch.application.job.db.service.PayService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

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

    @Bean
    public Job dbJob(Step basicStep) {
        return new JobBuilder("dbJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(basicStep)
                .build();
    }

    @Bean
    public Step basicStep() {
        return new StepBuilder("basicStep", jobRepository)
                .<Pay, Object>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemReader<Pay> itemReader() {
        // TODO: jobParameter
        List<Pay> list = payService.findByRequestDate("");
        return null;
    }

    private ItemProcessor<Pay, Object> itemProcessor() {
        return item -> {
            return null;
        };
    }

    private ItemWriter<Object> itemWriter() {
        return null;
    }
}
