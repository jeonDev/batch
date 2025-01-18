package com.jh.batch.application.job.db.domain;

import com.jh.batch.application.job.db.domain.entity.Balance;

import java.util.Optional;

public interface BalanceDao {

    Optional<Balance> findByMemberSeq(Long memberSeq);
}
