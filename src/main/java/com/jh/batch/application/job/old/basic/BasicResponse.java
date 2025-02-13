package com.jh.batch.application.job.old.basic;

import com.jh.batch.application.job.old.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.job.old.telegram.type.TelegramFieldType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
public class BasicResponse {

    @FixedLengthTelegram(size = 4)
    private String a;
    @FixedLengthTelegram(size = 4, point = 2, fieldType = TelegramFieldType.DECIMAL)
    private BigDecimal b;
    @FixedLengthTelegram(size = 4, fieldType = TelegramFieldType.INTEGER)
    private Integer c;
    @FixedLengthTelegram(size = 4)
    private String d;

}
