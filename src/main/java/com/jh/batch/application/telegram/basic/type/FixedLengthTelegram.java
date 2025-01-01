package com.jh.batch.application.telegram.basic.type;

import java.lang.annotation.*;

import static com.jh.batch.application.telegram.basic.type.TelegramFieldType.STRING;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLengthTelegram {
    int size();
    int point() default 0;
    TelegramFieldType fieldType() default STRING;
}
