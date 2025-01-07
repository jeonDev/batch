package com.jh.batch.application.telegram.writer;

import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
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
    private FlatFileHeaderCallback headerCallback;
    private FlatFileFooterCallback footerCallback;

    public TelegramBasicItemWriterBuilder(String name) {
        this.name = name;
    }

    public TelegramBasicItemWriterBuilder<T> filePath(String filePath) {
        this.resource = new FileSystemResource(filePath);
        return this;
    }

    public TelegramBasicItemWriterBuilder<T> lineAggregator(LineAggregator<T> lineAggregator) {
        this.lineAggregator = lineAggregator;
        return this;
    }

    public TelegramBasicItemWriterBuilder<T> headerCallback(FlatFileHeaderCallback headerCallback) {
        this.headerCallback = headerCallback;
        return this;
    }

    public TelegramBasicItemWriterBuilder<T> footerCallback(FlatFileFooterCallback footerCallback) {
        this.footerCallback = footerCallback;
        return this;
    }

    public FlatFileItemWriter<T> build() {
        return new FlatFileItemWriterBuilder<T>()
                .name(name)
                .resource(resource)
                .encoding(encoding)
                .lineAggregator(lineAggregator)
                .headerCallback(headerCallback)
                .footerCallback(footerCallback)
                .build();
    }
}
