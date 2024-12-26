package com.jh.batch.application.job;

import com.jh.batch.application.telegram.type.FixedLengthTelegram;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Test {
    @FixedLengthTelegram(start = 1, end = 4)
    private String a;
    @FixedLengthTelegram(start = 5, end = 8)
    private String b;
    @FixedLengthTelegram(start = 9, end = 12)
    private String c;
    @FixedLengthTelegram(start = 13, end = 16)
    private String d;
}
