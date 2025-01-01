package com.jh.batch.application.job.basic;

import com.jh.batch.application.telegram.basic.reader.FixedLengthFieldSetMapper;
import com.jh.batch.application.telegram.basic.reader.FixedLengthParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class BasicJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final String filePath;

    public BasicJobConfig(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.filePath = "/Users/jjh/project/file/batch/test";
    }

    @Bean
    public Job testJob(Step testStep) {
        return new JobBuilder("testJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(testStep)
                .build();
    }

    @Bean
    public Step testStep() {
        return new StepBuilder("testStep", jobRepository)
                .<BasicRequest, BasicResponse>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private FlatFileItemReader<BasicRequest> itemReader() {

        return new FlatFileItemReaderBuilder<BasicRequest>()
                .name("testReader")
                .resource(new FileSystemResource(filePath)) // 파일 경로
                .encoding("UTF-8") // 인코딩 설정
                .lineTokenizer(FixedLengthParser.createTokenizer(BasicRequest.class))
                .fieldSetMapper(new FixedLengthFieldSetMapper<>(BasicRequest.class))
                .build();
    }

    private ItemProcessor<BasicRequest, BasicResponse> itemProcessor() {
        return item -> {
            log.info(item.toString());
            return null;
        };
    }

    private ItemWriter<BasicResponse> itemWriter() {
        return new ItemWriter<BasicResponse>() {
            @Override
            public void write(Chunk<? extends BasicResponse> chunk) throws Exception {
                log.info(chunk.toString());
            }
        };
    }
}
