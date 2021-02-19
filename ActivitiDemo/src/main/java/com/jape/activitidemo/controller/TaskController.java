package com.jape.activitidemo.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
@RequestMapping("task")
public class TaskController {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    // 开始流程,启动流程实例
    @GetMapping("/start/{processKey}")
    public String startTask(@PathVariable String processKey) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", "Jape");
        identityService.setAuthenticatedUserId("JapeBetter");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey, variables);
        System.out.println(instance.getId());
        return instance.getId();

    }

    // 查询流程实例所在状态（获取流程走到的节点）
    @GetMapping("/getProcInstCurrTask/{procInstId}")
    public String getProcInstCurrTask(@PathVariable String procInstId) {
        /*//没用
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()// 创建流程实例查询
                .processInstanceId(procInstId)// 使用流程实例ID查询
                .active().singleResult();
        System.out.println(pi);

        //没用
        List<Execution> list2 = runtimeService.createExecutionQuery().processInstanceId(procInstId).list();
        System.out.println(list2);*/

        Task currTask = taskService.createTaskQuery().processInstanceId(procInstId).active().singleResult();
        System.out.println(currTask);
        return currTask.toString();
    }

    // 删除流程
    @GetMapping("/delete/{procInstId}")
    public String deleteTask(@PathVariable String procInstId) {
        runtimeService.deleteProcessInstance(procInstId, "测试删除");
        return procInstId;
    }

    // 根据任务id查找
    @GetMapping("/findById/{taskId}")
    public Object findById(@PathVariable String taskId) {
        // 查询当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        System.out.println(task);
        return task.toString();
    }

    // 根据操作人查询个人任务
        @GetMapping("/findByUser/{userId}")
    public Object findByUser(@PathVariable String userId) {
        List<Task> taskList = taskService.createTaskQuery()// 创建任务查询对象
                /** 查询条件（where部分） */
                .taskAssignee(userId)// 指定个人任务查询，指定办理人
//				.taskCandidateUser(userId)// 组任务的办理人查询
//				.processDefinitionId(processDefinitionId)// 使用流程定义ID查询
//				.processInstanceId(processInstanceId)// 使用流程实例ID查询
//				.executionId(executionId)// 使用执行对象ID查询
                /** 排序 */
                .orderByTaskCreateTime().asc()// 使用创建时间的升序排列
                /** 返回结果集 */
//                .singleResult()//返回惟一结果集
//                .count()//返回结果集的数量
//                .listPage(firstResult, maxResults);//分页查询
                .list();// 返回列表
        List<Map<String, Object>> datalist = taskList.stream().map(task -> {
            Map<String, Object> map = new HashMap<>();
            map.put("任务ID:", task.getId());
            map.put("任务名称:", task.getName());
            map.put("任务的创建时间:", task.getCreateTime());
            map.put("任务的办理人:", task.getAssignee());
            map.put("流程实例ID：", task.getProcessInstanceId());
            map.put("执行对象ID:", task.getExecutionId());
            map.put("流程定义ID:", task.getProcessDefinitionId());
            return map;
        }).collect(Collectors.toList());
        return datalist;
    }

    // 完成任务
    @GetMapping("/completeTask/{taskId}")
    public boolean completeTask(@PathVariable String taskId) {
        // 为任务指定代理人
        // taskService.claim(taskId, "Jape");
        // 完成此任务
        taskService.complete(taskId);
        return true;
    }


}
