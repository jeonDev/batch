package com.jh.batch.application.job.old.db.domain;

import com.jh.batch.application.job.old.db.domain.entity.Balance;

import java.util.Optional;

public interface BalanceDao {

    Optional<Balance> findByMemberSeq(Long memberSeq);
    void persist(Balance balance);
}
