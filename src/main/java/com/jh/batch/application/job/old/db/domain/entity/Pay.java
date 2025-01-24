package com.jh.batch.application.job.old.db.domain.entity;

import com.jh.batch.application.job.old.db.type.PayStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PAY_OLD")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private PayStatus payStatus;

    public void payComplete() {
        this.payStatus = PayStatus.COMPLETE;
    }
}
