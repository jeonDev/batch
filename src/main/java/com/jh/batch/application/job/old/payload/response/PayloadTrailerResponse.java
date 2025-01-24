package com.jh.batch.application.job.old.payload.response;

import com.jh.batch.application.job.old.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.job.old.telegram.type.TelegramResponse;
import lombok.Builder;

@Builder
public record PayloadTrailerResponse(
        @FixedLengthTelegram(size = 2) String division,
        @FixedLengthTelegram(size = 10) String data1
) implements TelegramResponse {
}
