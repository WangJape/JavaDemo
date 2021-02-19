package com.jape.activitidemo.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("history")
public class HistoryController {

    @Resource
    private HistoryService historyService;

    /**
     * 查询历史流程实例
     */
    @GetMapping("/findHistoryProcessInstance/{userId}")
    public Object findHistoryProcessInstance(@PathVariable String userId) {
        List<HistoricProcessInstance> hisProcInstList = historyService.createHistoricProcessInstanceQuery()// 创建历史流程实例查询
                .startedBy(userId)
                .orderByProcessInstanceStartTime().asc().list();
        List<Object> dataList = hisProcInstList.stream().map(hisProcInst -> {
            Map<String, Object> map = new HashMap();
            map.put("流程定义部署ID:", hisProcInst.getDeploymentId());
            map.put("流程定义Key:", hisProcInst.getProcessDefinitionKey());
            map.put("流程实例ID:", hisProcInst.getId());
            map.put("发起用户ID:", hisProcInst.getStartUserId());
            map.put("流程实例变量:", hisProcInst.getProcessVariables());
            map.put("流程实例开始时间:", hisProcInst.getStartTime());
            map.put("流程实例处理历时:", hisProcInst.getDurationInMillis());
            map.put("流程实例结束时间:", hisProcInst.getEndTime());
            return map;
        }).collect(Collectors.toList());
        return dataList;
    }

    /**
     * 查询历史流程变量
     */
    @GetMapping("/findHistoryProcessVariables/{processInstanceId}")
    public Object findHistoryProcessVariables(@PathVariable String processInstanceId) {
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        List<Object> dataList = list.stream().map(hisVarInst -> {
            Map<String, Object> map = new HashMap();
            map.put("变量ID:", hisVarInst.getId());
            map.put("流程实例ID:", hisVarInst.getProcessInstanceId());
            map.put("变量名:", hisVarInst.getVariableName());
            map.put("变量类型:", hisVarInst.getVariableTypeName());
            map.put("变量:", hisVarInst.getValue());
            return map;
        }).collect(Collectors.toList());
        return dataList;
    }

    /**
     * 历史活动查询接口
     */
    @GetMapping("/findHistoryActivity/{processInstanceId}")
    public Object findHistoryActivity(@PathVariable String processInstanceId) {
        List<HistoricActivityInstance> hisActInstList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();

        List<Object> dataList = hisActInstList.stream().map(hisActInst -> {
            Map<String, Object> map = new HashMap();
            map.put("记录id:", hisActInst.getId());
            map.put("活动id:", hisActInst.getActivityId());
            map.put("任务id:", hisActInst.getTaskId());
            map.put("执行对象ID:", hisActInst.getExecutionId());
            map.put("流程实例ID:", hisActInst.getProcessInstanceId());
            map.put("活动名称:", hisActInst.getActivityName());
            map.put("活动类型:", hisActInst.getActivityType());
            map.put("审批处理人:", hisActInst.getAssignee());
            map.put("活动开始时间:", hisActInst.getStartTime());
            map.put("活动处理历时:", hisActInst.getDurationInMillis());
            map.put("活动结束时间:", hisActInst.getEndTime());
            return map;
        }).collect(Collectors.toList());
        return dataList;
    }

    /**
     * 查询历史任务实例
     */
    @GetMapping("/findHistoryTaskInstance/{processInstanceId}")
    public Object findHistoryTaskInstance(@PathVariable String processInstanceId) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
        List<Map<String, Object>> datalist = list.stream().map(hisTaskInst -> {
            Map<String, Object> map = new HashMap();
            map.put("任务ID:", hisTaskInst.getId());
            map.put("执行对象ID:", hisTaskInst.getExecutionId());
            map.put("流程定义ID:", hisTaskInst.getProcessDefinitionId());
            map.put("流程实例ID:", hisTaskInst.getProcessInstanceId());
            map.put("任务名称:", hisTaskInst.getName());
            map.put("任务定义Key:", hisTaskInst.getTaskDefinitionKey());
            map.put("任务的办理人:", hisTaskInst.getAssignee());
            map.put("任务的创建时间:", hisTaskInst.getCreateTime());
            map.put("任务的结束时间", hisTaskInst.getEndTime());
            map.put("任务的持续时间", hisTaskInst.getDurationInMillis());
            return map;
        }).collect(Collectors.toList());
        return datalist;
    }




}
