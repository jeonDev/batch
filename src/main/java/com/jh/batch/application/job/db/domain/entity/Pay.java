package com.jh.batch.application.job.db.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "PAY")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requestDate")
    private String requestDate;

    @Column(name = "sendMemberSeq")
    private Long sendMemberSeq;

    @Column(name = "receiveMemberSeq")
    private Long receiveMemberSeq;

    @Column(name = "amount")
    private BigDecimal amount;
}
