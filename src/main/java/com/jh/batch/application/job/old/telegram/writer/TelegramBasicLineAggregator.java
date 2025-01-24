package com.jh.batch.application.job.old.telegram.writer;

import com.jh.batch.application.job.old.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.job.old.telegram.type.TelegramFieldType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Slf4j
public class TelegramBasicLineAggregator<T> implements LineAggregator<T> {

    @Override
    public String aggregate(T item) {
        StringBuilder sb = new StringBuilder();

        Field[] fields = item.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(FixedLengthTelegram.class)) {
                FixedLengthTelegram annotation = field.getAnnotation(FixedLengthTelegram.class);
                field.setAccessible(true);
                try {
                    Object telegramField = field.get(item);
                    sb.append(parseStr(telegramField, annotation.fieldType(), annotation.size(), annotation.point()));
                } catch (IllegalAccessException e) {
                    log.error("line aggregator Failed : {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    private String parseStr(Object item, TelegramFieldType fieldType, int length, int point) {
        if (fieldType == TelegramFieldType.STRING) {
            String value = String.format("%" + length + "." + length + "s", item);
            if (value.length() > length) throw new IllegalArgumentException("Telegram Length Unmatched");
            return value;
        } else if (fieldType == TelegramFieldType.INTEGER) {
            String value = String.format("%0" + length + "d", item);
            if (value.length() > length) throw new IllegalArgumentException("Telegram Length Unmatched");
            return value;
        } else if (fieldType == TelegramFieldType.DECIMAL) {
            BigDecimal decimal = (BigDecimal) item;
            if (point > 0) {
                decimal = decimal.multiply(BigDecimal.TEN.pow(point));
            }
            String value = String.format("%0" + length + "d", decimal.toBigInteger());
            if (value.length() > length) throw new IllegalArgumentException();
            return value;
        } else {
            throw new IllegalArgumentException("fieldType Undefined");
        }
    }
}
