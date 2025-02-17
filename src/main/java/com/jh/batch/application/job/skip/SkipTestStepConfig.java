package com.jh.batch.application.job.skip;

import com.jh.batch.application.job.skip.domain.SkipEntity;
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
public class SkipTestStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    private final EntityManagerFactory emf;
    private final static String STEP_NAME = "skipTestStep";
    private final Integer chunkSize = 10;

    @Bean
    public Step skipTestStep() {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<SkipEntity, SkipEntity>chunk(chunkSize, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(RuntimeException.class)
                .skipLimit(5)
                .build();
    }

    private ItemReader<SkipEntity> itemReader() {
        return new JdbcCursorItemReaderBuilder<SkipEntity>()
                .name("jdbcCursorDbItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(SkipEntity.class))
                .sql("SELECT * FROM SKIP")
                .build();
    }

    private ItemProcessor<SkipEntity, SkipEntity> itemProcessor() {
        return item -> {
            log.info("processor");
            item.check();
            if (true) throw new RuntimeException();
            return item;
        };
    }

    private ItemWriter<SkipEntity> itemWriter() {
        return new JpaItemWriterBuilder<SkipEntity>()
                .entityManagerFactory(emf)
                .build();
    }
}
