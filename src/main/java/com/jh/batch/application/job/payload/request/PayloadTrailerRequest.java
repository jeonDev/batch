package com.jh.batch.application.job.payload.request;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.type.TelegramRecord;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PayloadTrailerRequest implements TelegramRecord {
    @FixedLengthTelegram(size = 2)
    private String division;
    @FixedLengthTelegram(size = 10)
    private String data1;
}
