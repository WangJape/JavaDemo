package com.jape.kafkademo.aop;

import com.jape.kafkademo.entity.Log;
import com.jape.kafkademo.kafka.LogProducer;
import com.jape.kafkademo.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

@Aspect
@Component
public class ControllerAspect {

    @Autowired
    private LogProducer logProducer;

    @Pointcut("execution(* com.jape.kafkademo.controller.*.*(..))")
    public void cutPoint() {
    }

    @Around("cutPoint()")
    public Object recordLog(ProceedingJoinPoint point) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        long start = System.currentTimeMillis();
        Object response = null;
        try {
            response = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();

        Log log = new Log();
        log.setIp(IPUtils.getReqIpAddr());
        log.setReqPath(request.getRequestURI());
        log.setDate(LocalDate.now());
        log.setTime(LocalTime.now());
        log.setInterval(end - start);
        logProducer.sendLog(log);

        return response;

    }

}
