package com.jh.batch.application.job.old.db.domain.impl;

import com.jh.batch.application.job.old.db.domain.BalanceDao;
import com.jh.batch.application.job.old.db.domain.entity.Balance;
import com.jh.batch.application.job.old.db.domain.entity.BalanceRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BalanceDaoImpl implements BalanceDao {

    private final BalanceRepository balanceRepository;
    private final EntityManager em;

    public BalanceDaoImpl(BalanceRepository balanceRepository,
                          EntityManager em) {
        this.balanceRepository = balanceRepository;
        this.em = em;
    }

    @Override
    public Optional<Balance> findByMemberSeq(Long memberSeq) {
        return balanceRepository.findByMemberSeq(memberSeq);
    }

    @Override
    public void persist(Balance balance) {
        em.persist(balance);
    }
}
