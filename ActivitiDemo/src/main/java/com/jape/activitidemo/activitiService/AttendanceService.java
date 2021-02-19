package com.jape.activitidemo.activitiService;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class AttendanceService implements JavaDelegate {

    //流程变量
    private Expression exp;

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("serviceTask已经自动执行！,下一步发送邮件");
    }
}
