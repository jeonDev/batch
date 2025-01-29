package com.jh.batch.application.job.retry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@DynamicUpdate
@Entity
@Table(name = "RETRY")
public class RetryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long count;

    private String checkYn;

    public RetryEntity check() {
        this.checkYn = "Y";
        return this;
    }
}
