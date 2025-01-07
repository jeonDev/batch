package com.jh.batch.application.job.payload;

import com.jh.batch.application.job.payload.request.PayloadBodyRequest;
import com.jh.batch.application.job.payload.request.PayloadHeaderRequest;
import com.jh.batch.application.job.payload.request.PayloadTrailerRequest;
import com.jh.batch.application.job.payload.response.PayloadBodyResponse;
import com.jh.batch.application.job.payload.response.PayloadHeaderResponse;
import com.jh.batch.application.job.payload.response.PayloadTrailerResponse;
import com.jh.batch.application.telegram.processor.TelegramPayloadItemProcessor;
import com.jh.batch.application.telegram.reader.TelegramPayloadFixedLengthLineMapper;
import com.jh.batch.application.telegram.reader.TelegramPayloadItemReaderBuilder;
import com.jh.batch.application.telegram.type.TelegramRecord;
import com.jh.batch.application.telegram.type.TelegramResponse;
import com.jh.batch.application.telegram.writer.TelegramBasicItemWriterBuilder;
import com.jh.batch.application.telegram.writer.TelegramPayloadLineAggregator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
                .<TelegramRecord, TelegramResponse>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private FlatFileItemReader<TelegramRecord> itemReader() {
        LineMapper<TelegramRecord> lineMapper = TelegramPayloadFixedLengthLineMapper.of(
                PayloadHeaderRequest.class, "HH",
                PayloadBodyRequest.class, "BB",
                PayloadTrailerRequest.class, "TT");
        return new TelegramPayloadItemReaderBuilder<>("payloadItemReader", lineMapper)
                .filePath(rcvFilePath)
                .build();
    }

    private TelegramPayloadItemProcessor<PayloadHeaderRequest, PayloadBodyRequest, PayloadTrailerRequest, TelegramResponse> itemProcessor() {
        return new TelegramPayloadItemProcessor<>() {
            @Override
            protected TelegramResponse headerProcess(PayloadHeaderRequest item) {
                log.info("header Process : {}", item.toString());
                return PayloadHeaderResponse.builder()
                        .division(item.getDivision())
                        .data1(item.getData1())
                        .build();
            }

            @Override
            protected TelegramResponse bodyProcess(PayloadBodyRequest item) {
                log.info("body Process : {}", item.toString());
                return PayloadBodyResponse.builder()
                        .division(item.getDivision())
                        .data1(item.getData1())
                        .data2(item.getData2())
                        .data3(item.getData3())
                        .build();
            }

            @Override
            protected TelegramResponse trailerProcess(PayloadTrailerRequest item) {
                log.info("trailer Process : {}", item.toString());
                return PayloadTrailerResponse.builder()
                        .division(item.getDivision())
                        .data1(item.getData1())
                        .build();
            }
        };
    }

    private FlatFileItemWriter<TelegramResponse> itemWriter() {
        return new TelegramBasicItemWriterBuilder<TelegramResponse>("payloadItemWriter")
                .filePath(sndFilePath)
                .lineAggregator(new TelegramPayloadLineAggregator<>())
                .build();
    }
}
