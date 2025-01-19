package com.jh.batch.application.job.db;

import com.jh.batch.application.job.db.domain.entity.Balance;
import com.jh.batch.application.job.db.domain.entity.Pay;
import com.jh.batch.application.job.db.service.PayService;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DbJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    private final EntityManagerFactory emf;
    private final PayService payService;
    private final int chunkSize = 1;

    public DbJobConfig(JobRepository jobRepository,
                       PlatformTransactionManager transactionManager,
                       DataSource dataSource,
                       PayService payService,
                       EntityManagerFactory entityManagerFactory) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
        this.payService = payService;
        this.emf = entityManagerFactory;
    }

    @Bean
    public Job dbJob(Step dbStep) {
        return new JobBuilder("dbJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dbStep)
                .build();
    }

    @Bean
    public Step dbStep() {
        return new StepBuilder("dbStep", jobRepository)
                .<Pay, Object>chunk(chunkSize, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemReader<Pay> itemReader() {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .name("jdbcCursorDbItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .sql("SELECT * FROM PAY")
                .build();
    }

    private ItemProcessor<Pay, Object> itemProcessor() {
        return payService::process;
    }

    private ItemWriter<Object> itemWriter() {
        return new JpaItemWriterBuilder<>()
                .entityManagerFactory(emf)
                .build();
    }
}
