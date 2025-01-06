package com.jh.batch.application.telegram.reader;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.type.Telegram;
import com.jh.batch.application.telegram.type.TelegramFieldType;
import com.jh.batch.application.telegram.type.TelegramRecord;
import org.springframework.batch.item.file.LineMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class TelegramPayloadFixedLengthLineMapper<H extends TelegramRecord, B extends TelegramRecord, T extends TelegramRecord> implements LineMapper<TelegramRecord> {

    private final Telegram<H> header;
    private final Telegram<B> body;
    private final Telegram<T> trailer;

    private TelegramPayloadFixedLengthLineMapper(Telegram<H> header, Telegram<B> body, Telegram<T> trailer) {
        this.header = header;
        this.body = body;
        this.trailer = trailer;
    }

    public static <H extends TelegramRecord, B extends TelegramRecord, T extends TelegramRecord> TelegramPayloadFixedLengthLineMapper<H, B, T> of(Class<H> headerType, String headerDivision, Class<B> bodyType,  String bodyDivision, Class<T> trailerType, String trailerDivision) {
        return new TelegramPayloadFixedLengthLineMapper<>(
                new Telegram<>(headerType, headerDivision),
                new Telegram<>(bodyType, bodyDivision),
                new Telegram<>(trailerType, trailerDivision)
        );
    }

    @Override
    public TelegramRecord mapLine(String line, int lineNumber) throws Exception {
        if (line.length() < 2) throw new IllegalArgumentException("Line Length < 2");

        String division = line.substring(0, 2);
        Class<?> type = typeParser(division);

        TelegramRecord instance = (TelegramRecord) type.getDeclaredConstructor().newInstance();

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

    private Class<?> typeParser(String division) {
        if (header.division().equals(division)) {
            return header.data();
        } else if (body.division().equals(division)) {
            return body.data();
        } else if (trailer.division().equals(division)) {
            return trailer.data();
        }
        throw new IllegalArgumentException("Division Type Error");
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
