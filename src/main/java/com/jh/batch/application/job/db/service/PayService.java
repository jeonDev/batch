package com.jh.batch.application.job.db.service;

import com.jh.batch.application.job.db.domain.entity.Balance;

import java.util.Optional;

public interface PayService {
    Optional<Balance> findByBalance(Long memberSeq);
}
