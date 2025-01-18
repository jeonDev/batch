package com.jh.batch.application.job.db.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
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
