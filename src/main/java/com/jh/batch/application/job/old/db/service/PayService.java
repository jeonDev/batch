package com.jh.batch.application.job.old.db.service;

import com.jh.batch.application.job.old.db.domain.entity.Balance;
import com.jh.batch.application.job.old.db.domain.entity.Pay;

public interface PayService {
    Balance process(Pay pay);
}
