package com.jh.batch.application.telegram.basic.reader;

import com.jh.batch.application.telegram.basic.type.FixedLengthTelegram;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FixedLengthParser {

    public static FixedLengthTokenizer createTokenizer(Class<?> clazz) {
        List<Range> ranges = new ArrayList<>();
        List<String> names = new ArrayList<>();

        // 리플렉션을 통해 어노테이션 정보 추출
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FixedLengthTelegram.class)) {
                FixedLengthTelegram annotation = field.getAnnotation(FixedLengthTelegram.class);
                ranges.add(new Range(annotation.start(), annotation.end()));
                names.add(field.getName()); // 필드명 추가
            }
        }

        // Tokenizer 생성 및 설정
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setColumns(ranges.toArray(new Range[0])); // 구간 설정
        tokenizer.setNames(names.toArray(new String[0]));   // 필드명 설정
        return tokenizer;
    }

    public static BeanWrapperFieldSetMapper<?> createFieldSetMapper(Class<?> clazz) {
        BeanWrapperFieldSetMapper<Object> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(clazz); // 매핑 대상 클래스
        return fieldSetMapper;
    }
}
