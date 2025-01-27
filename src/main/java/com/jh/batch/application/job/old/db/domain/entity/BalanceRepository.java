package com.jh.batch.application.job.old.db.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByMemberSeq(Long memberSeq);
}
