package com.jh.batch.common.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobExecutionRepository extends JpaRepository<JobExecutionEntity, Long> {
}
