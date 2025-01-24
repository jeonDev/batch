package com.jh.batch.application.job.old.payload.request;

import com.jh.batch.application.job.old.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.job.old.telegram.type.TelegramFieldType;
import com.jh.batch.application.job.old.telegram.type.TelegramRecord;
import com.jh.batch.application.job.old.telegram.type.TelegramType;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class PayloadBodyRequest implements TelegramRecord {
    @FixedLengthTelegram(size = 2)
    private String division;
    @FixedLengthTelegram(size = 10)
    private String data1;
    @FixedLengthTelegram(size = 10, fieldType = TelegramFieldType.INTEGER)
    private Integer data2;
    @FixedLengthTelegram(size = 10, fieldType = TelegramFieldType.DECIMAL, point = 2)
    private BigDecimal data3;


    @Override
    public TelegramType getTelegramType() {
        return TelegramType.BODY;
    }
}
