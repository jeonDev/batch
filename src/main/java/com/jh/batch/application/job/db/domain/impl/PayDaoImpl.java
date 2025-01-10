package com.jh.batch.application.job.db.domain.impl;

import com.jh.batch.application.job.db.domain.PayDao;
import com.jh.batch.application.job.db.domain.entity.PayRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PayDaoImpl implements PayDao {
    private final PayRepository payRepository;

    public PayDaoImpl(PayRepository payRepository) {
        this.payRepository = payRepository;
    }


}
