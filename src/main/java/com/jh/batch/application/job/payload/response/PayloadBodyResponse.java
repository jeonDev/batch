package com.jh.batch.application.job.payload.response;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.type.TelegramFieldType;
import com.jh.batch.application.telegram.type.TelegramResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PayloadBodyResponse(
        @FixedLengthTelegram(size = 2) String division,
        @FixedLengthTelegram(size = 10) String data1,
        @FixedLengthTelegram(size = 10, fieldType = TelegramFieldType.INTEGER) Integer data2,
        @FixedLengthTelegram(size = 10, fieldType = TelegramFieldType.DECIMAL, point = 2) BigDecimal data3
) implements TelegramResponse {
}
