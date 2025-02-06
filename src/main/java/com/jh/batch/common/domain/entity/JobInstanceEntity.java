package com.jh.batch.common.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "JOB_INSTANCE_ENTITY")
public class JobInstanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    // version -> 같은 job 여러 번 실행 시 구분 값. 어디서 update?
    private Integer version;

    public static JobInstanceEntity of(String jobName, JobParameters jobParameters) {
        return JobInstanceEntity.builder()
                .jobName(jobName)
                .version(1)
                .build();
    }

    public JobInstance entityToBatchJobInstance() {
        JobInstance jobInstance = new JobInstance(getId(), getJobName());
        jobInstance.setVersion(getVersion());
        return jobInstance;
    }
}
