package com.jh.batch.application.job.db.domain.impl;

import com.jh.batch.application.job.db.domain.BalanceDao;
import com.jh.batch.application.job.db.domain.entity.Balance;
import com.jh.batch.application.job.db.domain.entity.BalanceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BalanceDaoImpl implements BalanceDao {

    private final BalanceRepository balanceRepository;

    public BalanceDaoImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Optional<Balance> findByMemberSeq(Long memberSeq) {
        return balanceRepository.findById(memberSeq);
    }
}
