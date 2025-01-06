package com.jh.batch.application.telegram.type;

public record Telegram<T extends TelegramRecord>(
        Class<T> data,
        String division
) {
}
