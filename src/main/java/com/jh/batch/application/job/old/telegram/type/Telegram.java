package com.jh.batch.application.job.old.telegram.type;

public record Telegram<T extends TelegramRecord>(
        Class<T> data,
        String division
) {
}
