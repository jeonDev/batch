package com.jh.batch.application.job.basic;

import com.jh.batch.application.telegram.basic.type.FixedLengthTelegram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BasicResponse {

    @FixedLengthTelegram(start = 1, end = 4)
    private String a;
    @FixedLengthTelegram(start = 5, end = 8)
    private String b;
    @FixedLengthTelegram(start = 9, end = 12)
    private String c;
    @FixedLengthTelegram(start = 13, end = 16)
    private String d;

}
