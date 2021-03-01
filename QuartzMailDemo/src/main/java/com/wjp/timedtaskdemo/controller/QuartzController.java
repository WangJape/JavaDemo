package com.wjp.timedtaskdemo.controller;

import com.wjp.timedtaskdemo.quartz.QuartzJob1;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

@Controller
@RequestMapping("quartz")
public class QuartzController {

    @Autowired
    private Scheduler scheduler;

    @GetMapping("add")
    @ResponseBody
    public String addJob() throws ClassNotFoundException, SchedulerException {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("myData", "测试数据");

        JobDetail jobDetail = newJob(QuartzJob1.class)
                .withIdentity("myJob", "jobGroup")
                .usingJobData(jobDataMap)
                .storeDurably(true) //job在运行完成之后是否在数据库中保留
                .build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger1", "triggerGroup1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return "Success";
    }

    @GetMapping("deleteAllJob")
    @ResponseBody
    public String deleteAllJob() throws SchedulerException {
        for(String group: scheduler.getJobGroupNames()) {
            for(JobKey jobKey : scheduler.getJobKeys(groupEquals(group))) {
                scheduler.deleteJob(jobKey);
                System.out.println("delete job identified by: " + jobKey);
            }
        }
        return "Success";
    }

    @GetMapping("deleteOneJob")
    @ResponseBody
    public String deleteOneJob(String jobName,String jobGroup) throws SchedulerException {
        JobKey jobKey = jobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
        System.out.println("delete job identified by: " + jobKey);
        return "Success";
    }

    @GetMapping("updateOneJob")
    @ResponseBody
    public String updateOneJob(String jobName,String jobGroup) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("myData", "测试更新数据");
        JobDetail jobDetail = newJob(QuartzJob1.class)
                .withIdentity(jobName, jobGroup)
                .usingJobData(jobDataMap)
                .storeDurably(true) //job在运行完成之后是否在数据库中保留
                .build();
        scheduler.addJob(jobDetail, true);
        return "Success";
    }

    @GetMapping("updateOneTrigger")
    @ResponseBody
    public String updateOneTrigger(String triggerName,String triggerGroup) throws SchedulerException {
        Trigger oldTrigger = scheduler.getTrigger(triggerKey("oldTrigger", "group1"));
        TriggerBuilder tb = oldTrigger.getTriggerBuilder();
        Trigger newTrigger = tb
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
        scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
        return "Success";
    }


    @GetMapping("queryAllJob")
    @ResponseBody
    public String queryAllJob() throws SchedulerException {
        for(String group: scheduler.getJobGroupNames()) {
            for(JobKey jobKey : scheduler.getJobKeys(groupEquals(group))) {
                System.out.println("Found job identified by: " + jobKey + "->" +
                        scheduler.getJobDetail(jobKey).getJobDataMap().get("myData"));
            }
        }
        return "Success";
    }


}
