package com.jh.batch.application.job.db.service.impl;

import com.jh.batch.application.job.db.domain.BalanceDao;
import com.jh.batch.application.job.db.domain.entity.Balance;
import com.jh.batch.application.job.db.domain.entity.Pay;
import com.jh.batch.application.job.db.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private final BalanceDao balanceDao;


    public PayServiceImpl(BalanceDao balanceDao) {
        this.balanceDao = balanceDao;
    }

    @Override
    public Balance process(Pay item) {

        log.info("item : {}", item.toString());
        Optional<Balance> optionalBalance = balanceDao.findByMemberSeq(item.getReceiveMemberSeq());

        if (optionalBalance.isEmpty()) {
            Balance entity = Balance.builder()
                    .balance(item.getAmount())
                    .memberSeq(item.getReceiveMemberSeq())
                    .build();

            balanceDao.persist(entity);
            return entity;
        }
        return optionalBalance.get().addBalance(item.getAmount());
    }
}
