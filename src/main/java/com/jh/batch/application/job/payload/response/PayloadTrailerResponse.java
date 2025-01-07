package com.jh.batch.application.job.payload.response;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import com.jh.batch.application.telegram.type.TelegramResponse;
import lombok.Builder;

@Builder
public record PayloadTrailerResponse(
        @FixedLengthTelegram(size = 2) String division,
        @FixedLengthTelegram(size = 10) String data1
) implements TelegramResponse {
}
