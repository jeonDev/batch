package com.jh.batch.application.telegram.processor;

import com.jh.batch.application.telegram.type.TelegramRecord;
import com.jh.batch.application.telegram.type.TelegramType;
import org.springframework.batch.item.ItemProcessor;

public abstract class TelegramPayloadItemProcessor<H extends TelegramRecord, B extends TelegramRecord, T extends TelegramRecord, O> implements ItemProcessor<TelegramRecord, O> {

    @Override
    @SuppressWarnings("unchecked")
    public O process(TelegramRecord item) {
        if (TelegramType.HEADER == item.getTelegramType()) {
            return headerProcess((H) item);
        } else if (TelegramType.BODY == item.getTelegramType()) {
            return bodyProcess((B) item);
        } else if (TelegramType.TRAILER == item.getTelegramType()) {
            return trailerProcess((T) item);
        } else {
            throw new IllegalArgumentException("Type UnMatched");
        }
    }

    protected abstract O headerProcess(H item);
    protected abstract O bodyProcess(B item);
    protected abstract O trailerProcess(T item);
}
