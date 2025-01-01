package com.jh.batch.application.telegram.basic.type;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLengthTelegram {
    int start();
    int end();
}
