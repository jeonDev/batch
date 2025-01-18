package com.jh.batch.application.job.db.service.impl;

import com.jh.batch.application.job.db.domain.BalanceDao;
import com.jh.batch.application.job.db.domain.entity.Balance;
import com.jh.batch.application.job.db.service.PayService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayServiceImpl implements PayService {

    private final BalanceDao balanceDao;


    public PayServiceImpl(BalanceDao balanceDao) {
        this.balanceDao = balanceDao;
    }

    @Override
    public Optional<Balance> findByBalance(Long memberSeq) {
        return balanceDao.findByMemberSeq(memberSeq);
    }
}
