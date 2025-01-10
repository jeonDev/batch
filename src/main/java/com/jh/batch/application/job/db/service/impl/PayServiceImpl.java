package com.jh.batch.application.job.db.service.impl;

import com.jh.batch.application.job.db.domain.PayDao;
import com.jh.batch.application.job.db.domain.entity.Pay;
import com.jh.batch.application.job.db.service.PayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    private final PayDao payDao;

    public PayServiceImpl(PayDao payDao) {
        this.payDao = payDao;
    }

    public List<Pay> reader() {
        return null;
    }

    @Override
    public List<Pay> findByRequestDate(String requestDate) {
        return null;
    }
}
