package com.jh.batch.application.telegram.reader;

import com.jh.batch.application.telegram.type.TelegramRecord;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class TelegramPayloadItemReaderBuilder<T extends TelegramRecord> {
    // TODO..
    private String name;
    private Resource resource;
    private String encoding = "UTF-8";
    private LineMapper<T> lineMapper;

    public TelegramPayloadItemReaderBuilder(String name, LineMapper<T> lineMapper) {
        this.name = name;
        this.lineMapper = lineMapper;
    }

    public TelegramPayloadItemReaderBuilder<T> filePath(String filePath) {
        this.resource = new FileSystemResource(filePath);
        return this;
    }

    public FlatFileItemReader<T> build() {
        return new FlatFileItemReaderBuilder<T>()
                .name(name)
                .resource(resource)
                .encoding(encoding)
                .lineMapper(lineMapper)
                .build();
    }
}
