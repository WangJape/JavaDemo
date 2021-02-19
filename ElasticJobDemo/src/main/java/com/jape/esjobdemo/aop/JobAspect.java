package com.jape.esjobdemo.aop;

import com.dangdang.ddframe.job.api.ShardingContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Job切面计时
 *
 * @author Jape
 * @since 2020/11/16 15:24
 */
@Slf4j
@Aspect
@Component
public class JobAspect {

    @Pointcut("execution(* com.jape.esjobdemo.job.*.*(..))")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object printTime(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        ShardingContext context = (ShardingContext) args[0];
        String jobName = context.getJobName();
        log.info("[{}]任务开始执行", jobName);
        long startTime = System.currentTimeMillis();

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("[{}]任务执行异常", jobName, throwable);
        }

        long endTime = System.currentTimeMillis();
        log.info("[{}]任务执行结束,用时[{}]ms", jobName, (endTime - startTime));
        return proceed;
    }
}
