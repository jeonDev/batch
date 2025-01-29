package com.jh.batch.application.job.retry;

import com.jh.batch.application.job.retry.domain.RetryEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
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
@RequiredArgsConstructor
@Configuration
public class RetryTestStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    private final EntityManagerFactory emf;
    private final static String STEP_NAME = "retryTestStep";
    private final Integer chunkSize = 10;

    @Bean
    public Step retryTestStep() {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<RetryEntity, RetryEntity>chunk(chunkSize, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .retry(RuntimeException.class)
                .retryLimit(1)
                .build();
    }

    private ItemReader<RetryEntity> itemReader() {
        return new JdbcCursorItemReaderBuilder<RetryEntity>()
                .name("jdbcCursorDbItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(RetryEntity.class))
                .sql("SELECT * FROM RETRY")
                .build();
    }

    private ItemProcessor<RetryEntity, RetryEntity> itemProcessor() {
        return item -> {
            log.info("processor");
            item.check();
            if (2L == item.getCount()) throw new RuntimeException("Runtime Error");
            log.info("No Exception");
            return item;
        };
    }

    private ItemWriter<RetryEntity> itemWriter() {
        return new JpaItemWriterBuilder<RetryEntity>()
                .entityManagerFactory(emf)
                .build();
    }
}
