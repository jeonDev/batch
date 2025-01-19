package com.jh.batch.application.job.db.service;

import com.jh.batch.application.job.db.domain.entity.Balance;
import com.jh.batch.application.job.db.domain.entity.Pay;

public interface PayService {
    Balance process(Pay pay);
}
