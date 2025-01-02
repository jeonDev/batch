package com.jh.batch.application.telegram.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;

public class TelegramBasicItemWriterBuilder<T> {
    private String name;
    private String encoding = "UTF-8";
    private WritableResource resource;
    private LineAggregator<T> lineAggregator;

    public TelegramBasicItemWriterBuilder(String name) {
        this.name = name;
        this.lineAggregator = new TelegramBasicLineAggregator<>();
    }

    public TelegramBasicItemWriterBuilder<T> filePath(String filePath) {
        this.resource = new FileSystemResource(filePath);
        return this;
    }

    public FlatFileItemWriter<T> build() {
        return new FlatFileItemWriterBuilder<T>()
                .name(name)
                .resource(resource)
                .encoding(encoding)
                .lineAggregator(lineAggregator)
                .build();
    }
}
