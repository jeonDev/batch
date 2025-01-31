package com.jh.batch.application.job.old.telegram.reader;

import com.jh.batch.application.job.old.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.job.old.telegram.type.TelegramFieldType;
import org.springframework.batch.item.file.LineMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class TelegramBasicFixedLengthLineMapper<T> implements LineMapper<T> {

    private final Class<T> type;

    private TelegramBasicFixedLengthLineMapper(Class<T> type) {
        this.type = type;
    }

    public static <T> TelegramBasicFixedLengthLineMapper<T> of(Class<T> type) {
        return new TelegramBasicFixedLengthLineMapper<>(type);
    }

    @Override
    public T mapLine(String line, int lineNumber) throws Exception {
        T instance = type.getDeclaredConstructor().newInstance();
        Field[] fields = type.getDeclaredFields();
        int start = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(FixedLengthTelegram.class)) {
                FixedLengthTelegram annotation = field.getAnnotation(FixedLengthTelegram.class);
                int size = annotation.size();
                int point = annotation.point();
                TelegramFieldType fieldType = annotation.fieldType();

                field.setAccessible(true);
                field.set(instance, lineParser(fieldType, line, start, size, point));

                start += size;
            }
        }
        return instance;
    }

    private Object lineParser(TelegramFieldType fieldType, String line, int start, int size, int point) {
        int end = start + size;

        if (fieldType == TelegramFieldType.STRING) {
            return line.substring(start, end).trim();
        } else if (fieldType == TelegramFieldType.INTEGER) {
            return Integer.parseInt(line.substring(start, end));
        } else if (fieldType == TelegramFieldType.DECIMAL) {
            String telegram = line.substring(start, end);
            if (point > 0) {
                String decimal = telegram.substring(0, size - point)
                        .concat(".")
                        .concat(telegram.substring(size - point));
                return new BigDecimal(decimal);
            }
            return new BigDecimal(telegram);
        } else {
            throw new IllegalArgumentException("fieldType Undefined");
        }
    }
}
