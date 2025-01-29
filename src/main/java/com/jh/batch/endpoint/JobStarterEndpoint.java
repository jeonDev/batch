package com.jh.batch.endpoint;

import com.jh.batch.common.executor.JobExecutor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobStarterEndpoint {

    private final JobExecutor jobExecutor;
    private final ApplicationContext applicationContext;

    public JobStarterEndpoint(JobExecutor jobExecutor,
                              ApplicationContext applicationContext) {
        this.jobExecutor = jobExecutor;
        this.applicationContext = applicationContext;
    }

    @GetMapping("/v1/job/{jobName}")
    public String jobExecute(@PathVariable(name = "jobName") String jobName, @RequestParam(name = "dt", required = false) String dt) {

        Job job = applicationContext.getBean(jobName, Job.class);
        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("dt", dt)
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();
        jobExecutor.execute(job, jobParameters);
        return "ok";
    }
}
