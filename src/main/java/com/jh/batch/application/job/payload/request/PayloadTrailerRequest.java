package com.jh.batch.application.job.payload.request;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.type.TelegramRecord;

public record PayloadTrailerRequest(
        @FixedLengthTelegram(size = 2) String division,
        @FixedLengthTelegram(size = 10) String data1
) implements TelegramRecord {
}
