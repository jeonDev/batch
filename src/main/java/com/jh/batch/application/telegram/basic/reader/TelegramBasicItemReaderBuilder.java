package com.jh.batch.application.telegram.basic.reader;

import com.jh.batch.application.job.basic.BasicRequest;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class TelegramBasicItemReaderBuilder<T> {
    private String name;
    private Resource resource;
    private String encoding = "UTF-8";
//    private LineTokenizer lineTokenizer;
//    private FieldSetMapper<T> fieldSetMapper;
    private LineMapper<T> lineMapper;

    public TelegramBasicItemReaderBuilder(String name, Class<T> type) {
        this.name = name;
        lineMapper = TelegramBasicFixedLengthLineMapper.of(type);
//        lineTokenizer = FixedLengthParser.createTokenizer(type);
//        fieldSetMapper = new FixedLengthFieldSetMapper<>(type);
    }

    public TelegramBasicItemReaderBuilder<T> filePath(String filePath) {
        this.resource = new FileSystemResource(filePath);
        return this;
    }

    public FlatFileItemReader<T> build() {
        return new FlatFileItemReaderBuilder<T>()
                .name(name)
                .resource(resource)
                .encoding(encoding)
                .lineMapper(lineMapper)
//                .lineTokenizer(lineTokenizer)
//                .fieldSetMapper(fieldSetMapper)
                .build();
    }
}
