package com.jh.batch.common.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "JOB_EXECUTION_ENTITY")
public class JobExecutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobInstanceId;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;

    @Enumerated(EnumType.STRING)
    private BatchStatus exitStatus;
    private LocalDateTime startTime;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private LocalDateTime lastUpdated;
    private Integer version;

    public static JobExecutionEntity of(Long jobInstanceId) {
        return JobExecutionEntity.builder()
                .jobInstanceId(jobInstanceId)
                .startTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .status(BatchStatus.STARTED)
                .version(1)
                .build();
    }

    public JobExecution entityToBatchJobExecution(JobInstance jobInstance, JobParameters jobParameters) {
        JobExecution jobExecution = new JobExecution(jobInstance, id, jobParameters);
        jobExecution.setStartTime(startTime);
        jobExecution.setCreateTime(createTime);
        jobExecution.setStatus(status);
        jobExecution.setVersion(version);
        return jobExecution;
    }

}
