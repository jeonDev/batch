package com.jh.batch.application.job.payload.request;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.type.TelegramFieldType;
import com.jh.batch.application.telegram.type.TelegramRecord;

public record PayloadBodyRequest(
        @FixedLengthTelegram(size = 2) String division,
        @FixedLengthTelegram(size = 10) String data1,
        @FixedLengthTelegram(size = 10, fieldType = TelegramFieldType.INTEGER) String data2,
        @FixedLengthTelegram(size = 10, fieldType = TelegramFieldType.DECIMAL, point = 2) String data3
) implements TelegramRecord {

}
