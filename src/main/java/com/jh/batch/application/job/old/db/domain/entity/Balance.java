package com.jh.batch.application.job.old.db.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "BALANCE_OLD")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "memberSeq", unique = true)
    private Long memberSeq;

    @Column(name = "balance")
    private BigDecimal balance;

    public Balance addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        return this;
    }
}
