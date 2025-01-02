package com.jh.batch.application.job.payload;

import com.jh.batch.application.job.basic.BasicRequest;
import com.jh.batch.application.job.basic.BasicResponse;
import com.jh.batch.application.telegram.reader.TelegramBasicItemReaderBuilder;
import com.jh.batch.application.telegram.writer.TelegramBasicItemWriterBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class PayloadJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final String rcvFilePath;
    private final String sndFilePath;

    public PayloadJobConfig(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.rcvFilePath = "/Users/jjh/project/file/batch/test";
        this.sndFilePath = "/Users/jjh/project/file/batch/test_w";
    }


    @Bean
    public Job payloadJob(Step payloadStep) {
        return new JobBuilder("payloadJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(payloadStep)
                .build();
    }

    @Bean
    public Step payloadStep() {
        return new StepBuilder("payloadStep", jobRepository)
                .<BasicRequest, BasicResponse>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private FlatFileItemReader<BasicRequest> itemReader() {

        return new TelegramBasicItemReaderBuilder<>("payloadItemReader", BasicRequest.class)
                .filePath(rcvFilePath)
                .build();
    }

    private ItemProcessor<BasicRequest, BasicResponse> itemProcessor() {
        return item -> {
            log.info(item.toString());
            return new BasicResponse(item.getA(), item.getB(), item.getC(), item.getD());
        };
    }

    private FlatFileItemWriter<BasicResponse> itemWriter() {
        return new TelegramBasicItemWriterBuilder<BasicResponse>("payloadItemWriter")
                .filePath(sndFilePath)
                .build();

    }
}
