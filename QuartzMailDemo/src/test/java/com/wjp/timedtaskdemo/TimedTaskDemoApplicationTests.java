package com.wjp.timedtaskdemo;

import com.wjp.timedtaskdemo.quartz.QuartzJob1;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.GregorianCalendar;
import java.util.concurrent.locks.LockSupport;

@SpringBootTest
class TimedTaskDemoApplicationTests {

    @Test
    void testQuartz() throws SchedulerException {

        // 1、创建调度器Scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、构建Trigger实例,每隔1s执行一次
        TriggerBuilder triggerBuilder =  TriggerBuilder.newTrigger();
        Trigger trigger = triggerBuilder
                .withIdentity("trigger1", "triggerGroup1")
                .startAt(new GregorianCalendar(2020,3,13,11,42).getTime())
                //.startNow()//立即生效, 启动时会触发一次
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)//每隔1s执行一次
                        .repeatForever())//一直执行
                        //.withRepeatCount(5))//执行5次;
                .endAt(new GregorianCalendar(2020,3,13,11,43).getTime())
                .build();

        // 3、创建JobDetail实例，并与QuartzJob1类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob1.class)
                .withIdentity("wjpJob1", "wjpGroup1")
                .usingJobData("wjpData","我是Job中的自定义数据")
                .build();

        // 4、调度器中加入Job任务和触发器
        scheduler.scheduleJob(jobDetail, trigger);

        // 5、启动，内部所有注册的触发器开始计时
        scheduler.start();

        LockSupport.park("调度器");

        scheduler.shutdown();
    }



    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    void testSendQQMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1143828481@qq.com");
        message.setTo("2577353996@qq.com");
        message.setSubject("建鹏邮件发送测试");
        message.setText("建鹏邮件发送测试内容，建鹏邮件发送测试内容");
        javaMailSender.send(message);
    }
}
