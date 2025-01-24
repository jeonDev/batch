package com.jh.batch.application.job.old.telegram.type;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLengthTelegram {
    int size();
    int point() default 0;
    TelegramFieldType fieldType() default TelegramFieldType.STRING;
}
