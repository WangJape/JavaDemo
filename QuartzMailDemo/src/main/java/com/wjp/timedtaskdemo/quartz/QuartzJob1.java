package com.wjp.timedtaskdemo.quartz;

import org.quartz.*;
import java.time.LocalDateTime;

/**
 * 定时任务的任务
 * @DisallowConcurrentExecution -> 不允许并行执行
 */
@DisallowConcurrentExecution
public class QuartzJob1 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail job = jobExecutionContext.getJobDetail();
        System.err.print(LocalDateTime.now().toString());
        System.err.print(job.getKey().getName());
        System.err.println(job.getJobDataMap().getString("wjpData"));
    }
}
