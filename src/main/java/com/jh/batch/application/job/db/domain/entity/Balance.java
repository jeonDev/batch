package com.jh.batch.application.job.db.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "BALANCE")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "memberSeq")
    private Long memberSeq;

    @Column(name = "balance")
    private BigDecimal balance;

}
