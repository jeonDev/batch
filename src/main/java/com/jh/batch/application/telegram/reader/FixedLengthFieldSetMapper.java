package com.jh.batch.application.telegram.reader;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.lang.reflect.Field;

@Deprecated
public class FixedLengthFieldSetMapper<T> implements FieldSetMapper<T> {

    private final Class<T> type;

    public FixedLengthFieldSetMapper(Class<T> type) {
        this.type = type;
    }
    @Override
    public T mapFieldSet(FieldSet fieldSet) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();

            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(FixedLengthTelegram.class)) {
                    String fieldName = field.getName(); // 필드명
                    String value = fieldSet.readString(fieldName); // 필드값 읽기

                    field.setAccessible(true);
                    field.set(instance, value); // 값 주입
                }
            }

            return instance;

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to map fields", e);
        }
    }
}
