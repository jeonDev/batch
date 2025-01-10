package com.jh.batch.application.job.db.service;

import com.jh.batch.application.job.db.domain.entity.Pay;

import java.util.List;

public interface PayService {
    List<Pay> findByRequestDate(String requestDate);
}
