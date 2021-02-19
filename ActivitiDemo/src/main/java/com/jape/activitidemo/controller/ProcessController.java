package com.jape.activitidemo.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("processDef")
public class ProcessController {

	@Resource
	private RepositoryService repositoryService;

	@GetMapping("/getAll")
	public Object getAll() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
				.orderByProcessDefinitionKey().asc().list();

		List<Map<String, Object>> datalist = list.stream().map(procdef -> {
			Map<String, Object> map = new HashMap();
			map.put("流程定义ID:", procdef.getId());// 流程定义的key+版本+随机生成数
			map.put("流程定义的名称:", procdef.getName());// 对应hello.bpmn文件中的name属性值
			map.put("流程定义的key:", procdef.getKey());// 对应hello.bpmn文件中的id属性值
			map.put("流程定义的版本:", procdef.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
			map.put("资源名称bpmn文件:", procdef.getResourceName());
			map.put("资源名称png文件:", procdef.getDiagramResourceName());
			map.put("部署对象ID：", procdef.getDeploymentId());
			return map;
		}).collect(Collectors.toList());
		return datalist;
	}

	// 获取流程图片
	@GetMapping("/getImg/{procDefId}")
	public void getImg(@PathVariable String procDefId, HttpServletResponse response) throws IOException {
		InputStream inputStream = repositoryService.getProcessDiagram(procDefId);
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		inputStream.close();

		response.setContentType("image/*");
		ServletOutputStream outputStream = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", outputStream);
		outputStream.close();
	}

	// 从classpath:processes下读取
	@GetMapping("/deploy/{bpmnFileName}")
	public String deployProcess(@PathVariable String bpmnFileName) {
		Deployment deploy = repositoryService.createDeployment().name(bpmnFileName)
				.addClasspathResource("processes/" + bpmnFileName + ".bpmn")
				.deploy();
		return deploy.getId();
	}

	@GetMapping("/delete/{deploymentId}")
	public boolean getAll(@PathVariable String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
		return true;
	}
}
