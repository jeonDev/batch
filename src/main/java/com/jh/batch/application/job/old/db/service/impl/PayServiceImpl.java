package com.jh.batch.application.job.old.db.service.impl;

import com.jh.batch.application.job.old.db.domain.BalanceDao;
import com.jh.batch.application.job.old.db.domain.PayDao;
import com.jh.batch.application.job.old.db.domain.entity.Balance;
import com.jh.batch.application.job.old.db.domain.entity.Pay;
import com.jh.batch.application.job.old.db.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private final PayDao payDao;
    private final BalanceDao balanceDao;

    public PayServiceImpl(PayDao payDao,
                          BalanceDao balanceDao) {
        this.payDao = payDao;
        this.balanceDao = balanceDao;
    }

    @Override
    public Balance process(Pay item) {

        log.info("item : {}", item.toString());
        item.payComplete();
        payDao.save(item);
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
