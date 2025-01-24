package com.jh.batch.application.job.old.payload.request;

import com.jh.batch.application.job.old.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.job.old.telegram.type.TelegramRecord;
import com.jh.batch.application.job.old.telegram.type.TelegramType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PayloadTrailerRequest implements TelegramRecord {
    @FixedLengthTelegram(size = 2)
    private String division;
    @FixedLengthTelegram(size = 10)
    private String data1;

    @Override
    public TelegramType getTelegramType() {
        return TelegramType.TRAILER;
    }
}
