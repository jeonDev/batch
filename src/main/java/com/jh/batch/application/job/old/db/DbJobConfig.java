package com.jh.batch.application.job.old.db;

import com.jh.batch.application.job.old.db.domain.entity.Pay;
import com.jh.batch.application.job.old.db.service.PayService;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
//@Configuration
@Deprecated
public class DbJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    private final EntityManagerFactory emf;
    private final PayService payService;
    private final int chunkSize = 10;

    /**
     * 1. writer에서 예외 발생 시, process 롤백??
     * */
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
    @JobScope
    public Step dbStep(@Value("#{jobParameters[dt]}") String dt) {
        return new StepBuilder("dbStep", jobRepository)
                .<Pay, Object>chunk(chunkSize, transactionManager)
                .allowStartIfComplete(true)
                .reader(itemReader(dt))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemReader<Pay> itemReader(String dt) {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .name("jdbcCursorDbItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .sql("SELECT * FROM PAY WHERE STATUS = 'READY' AND REQUEST_DATE = ?")
                .preparedStatementSetter(ps -> ps.setString(1, dt))
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
