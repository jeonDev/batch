package com.jh.batch.common.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.batch.core.*;

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

    private String exitStatus;
    @Column(length = 500)
    private String exitDescription;
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

    public void update(JobExecution jobExecution) {
        this.endTime = jobExecution.getEndTime();
        this.lastUpdated = jobExecution.getLastUpdated();
        this.status = jobExecution.getStatus();
        this.exitStatus = jobExecution.getExitStatus().getExitCode();
        String exitDescription = jobExecution.getExitStatus().getExitDescription();
        this.exitDescription = exitDescription.length() > 500 ? exitDescription.substring(0, 500) : exitDescription;
    }

}
