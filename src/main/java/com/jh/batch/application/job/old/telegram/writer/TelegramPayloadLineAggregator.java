package com.jh.batch.application.job.old.telegram.writer;

import com.jh.batch.application.job.old.telegram.type.TelegramResponse;
import org.springframework.batch.item.file.transform.LineAggregator;

public class TelegramPayloadLineAggregator<T extends TelegramResponse> implements LineAggregator<TelegramResponse> {

    @Override
    public String aggregate(TelegramResponse item) {
        return item.parse();
    }
}
