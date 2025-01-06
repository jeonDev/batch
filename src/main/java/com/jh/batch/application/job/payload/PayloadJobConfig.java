package com.jh.batch.application.job.payload;

import com.jh.batch.application.job.basic.BasicResponse;
import com.jh.batch.application.job.payload.request.PayloadBodyRequest;
import com.jh.batch.application.job.payload.request.PayloadHeaderRequest;
import com.jh.batch.application.job.payload.request.PayloadTrailerRequest;
import com.jh.batch.application.telegram.reader.TelegramPayloadFixedLengthLineMapper;
import com.jh.batch.application.telegram.reader.TelegramPayloadItemReaderBuilder;
import com.jh.batch.application.telegram.type.TelegramRecord;
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
import org.springframework.batch.item.file.LineMapper;
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
        this.rcvFilePath = "/Users/jjh/project/file/batch/test1";
        this.sndFilePath = "/Users/jjh/project/file/batch/test1_w";
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
                .<TelegramRecord, BasicResponse>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private FlatFileItemReader<TelegramRecord> itemReader() {
        LineMapper<TelegramRecord> lineMapper = TelegramPayloadFixedLengthLineMapper.of(PayloadHeaderRequest.class, "HH",
                PayloadBodyRequest.class, "BB",
                PayloadTrailerRequest.class, "TT");
        return new TelegramPayloadItemReaderBuilder<>("payloadItemReader", lineMapper)
                .filePath(rcvFilePath)
                .build();
    }

    private ItemProcessor<TelegramRecord, BasicResponse> itemProcessor() {
        return item -> {
            log.info(item.toString());
//            return new BasicResponse(item.getA(), item.getB(), item.getC(), item.getD());
            return null;
        };
    }

    private FlatFileItemWriter<BasicResponse> itemWriter() {
        return new TelegramBasicItemWriterBuilder<BasicResponse>("payloadItemWriter")
                .filePath(sndFilePath)
                .build();

    }
}
