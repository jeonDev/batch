package com.jh.batch.application.telegram.basic.writer;

import com.jh.batch.application.telegram.basic.type.FixedLengthTelegram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.lang.reflect.Field;

@Slf4j
public class TelegramBasicLineAggregator<T> implements LineAggregator<T> {

    @Override
    public String aggregate(T item) {
        StringBuilder sb = new StringBuilder();

        Field[] fields = item.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(FixedLengthTelegram.class)) {
                field.setAccessible(true);
                try {
                    sb.append(field.get(item));
                } catch (IllegalAccessException e) {
                    log.error("line aggregator Failed : {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }
}
