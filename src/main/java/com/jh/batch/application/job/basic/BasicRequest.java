package com.jh.batch.application.job.basic;

import com.jh.batch.application.telegram.basic.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.basic.type.TelegramFieldType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class BasicRequest {
    @FixedLengthTelegram(size = 4)
    private String a;
    @FixedLengthTelegram(size = 4, point = 2, fieldType = TelegramFieldType.DECIMAL)
    private BigDecimal b;
    @FixedLengthTelegram(size = 4, fieldType = TelegramFieldType.INTEGER)
    private Integer c;
    @FixedLengthTelegram(size = 4)
    private String d;
}
