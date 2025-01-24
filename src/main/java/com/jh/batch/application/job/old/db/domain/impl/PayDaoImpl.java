package com.jh.batch.application.job.old.db.domain.impl;

import com.jh.batch.application.job.old.db.domain.PayDao;
import com.jh.batch.application.job.old.db.domain.entity.Pay;
import com.jh.batch.application.job.old.db.domain.entity.PayRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PayDaoImpl implements PayDao {
    private final PayRepository payRepository;

    public PayDaoImpl(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    @Override
    public Pay save(Pay pay) {
        return payRepository.save(pay);
    }
}
