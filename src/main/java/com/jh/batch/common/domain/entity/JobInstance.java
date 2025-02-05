package com.jh.batch.common.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.batch.core.JobParameters;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "JOB_INSTANCE")
public class JobInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    // version -> 같은 job 여러 번 실행 시 구분 값. 어디서 update?
    private Integer version;

    public static JobInstance of(String jobName, JobParameters jobParameters) {
        return JobInstance.builder()
                .jobName(jobName)
                .version(1)
                .build();
    }

    public org.springframework.batch.core.JobInstance entityToBatchJobInstance() {
        org.springframework.batch.core.JobInstance jobInstance = new org.springframework.batch.core.JobInstance(getId(), getJobName());
        jobInstance.setVersion(getVersion());
        return jobInstance;
    }
}
