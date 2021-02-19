package com.jape.activitidemo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@SpringBootTest
class ActivitiDemoApplicationTests {

	/**
	 * RepositoryService: 流程仓库Service，用于管理流程仓库，例如：部署，删除，读取流程资源
	 * IdentityService：身份Service，可以管理，查询用户，组
	 * RuntimeService：运行时Service，可以处理所有正在运行状态的流程实例，任务等
	 * TaskService：任务Service，用于管理，查询任务，例如：签收，办理，指派等会跟踪 需要由用户手动执行的任务是Activiti API的核心。我们可以使用此服务创建任务，声明并完成任务，分配任务的受让人等。
	 * HistoryService：历史Service，可以查询所有历史数据，例如：流程实例，任务，活动，变量，附件等
	 * FormService：表单Service，用于读取和流程，任务相关的表单数据
	 * ManagementService：引擎管理Service，和具体业务无关，主要是可以查询引擎配置，数据库，作业等，与元数据相关，在创建应用程序时通常不需要。
	 * DynamicBpmnService：一个新增的服务，用于动态修改流程中的一些参数信息等，是引擎中的一个辅助的服务
	 */
	
	/**
	 * ACT_GE_* : （通用），用在各种情况下；通用的流程定义和流程资源、系统相关属性
	 * 		act_evt_log	事件处理日志表
	 * 	    act_ge_bytearray	通用的流程定义和流程资源
	 * 	    act_ge_property	系统相关属性
	 * ACT_HI_* : （历史），这些表中保存的都是历史数据，比如执行过的流程实例、变量、任务，等等。Activit默认提供了4种历史级别：
	 * 		act_hi_actinst	历史的流程实例
	 * 		act_hi_attachment	历史的流程附件
	 * 		act_hi_comment	历史的说明性信息
	 * 		act_hi_detail	历史的流程运行中的细节信息
	 * 		act_hi_identitylink	历史的流程运行过程中用户关系
	 * 		act_hi_procinst	历史的流程实例
	 * 		act_hi_taskinst	历史的任务实例
	 * 		act_hi_varinst	历史的流程运行中的变量信息
	 * ACT_ID_* : （身份），这些表中保存的都是身份信息，如用户和组以及两者之间的关系。如果Activiti被集成在某一系统当中的话，这些表可以不用，可以直接使用现有系统中的用户或组信息；
	 * 		act_id_group	身份信息-组信息
	 * 		act_id_info	身份信息-组信息
	 * 		act_id_membership	身份信息-用户和组关系的中间表
	 * 		act_id_user	身份信息-用户信息
	 * 		act_procdef_info	死信任务
	 * ACT_RE_* : （仓库），这些表中保存一些‘静态’信息，如流程定义和流程资源（如图片、规则等）；
	 * 		act_re_deployment	部署单元信息
	 * 		act_re_model	模型信息
	 * 		act_re_procdef	已部署的流程定义
	 * ACT_RU_* : （运行时），这些表中保存一些流程实例、用户任务、变量等的运行时数据。Activiti只保存流程实例在执行过程中的运行时数据，并且当流程结束后会立即移除这些数据，这是为了保证运行时表尽量的小并运行的足够快
	 * 		act_ru_deadletter_job	执行失败任务表
	 *  	act_ru_event_subscr	运行时事件
	 *  	act_ru_execution	运行时流程执行实例
	 *  	act_ru_identitylink	运行时用户关系信息
	 *  	act_ru_job	运行时作业
	 *  	act_ru_suspended_job	运行时暂停任务
	 *  	act_ru_task	运行时任务
	 *  	act_ru_timer_job	运行时定时任务
	 *  	act_ru_variable	运行时变量表
	 */
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private HistoryService historyService;
	@Resource
	private RepositoryService repositoryService;

	/**
	 * 部署流程定义（从classpath）
	 */
	@Test
	public void deploymentProcessDefinition_classpath() {
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("手动部署流程定义")// 添加部署的名称
				.addClasspathResource("bpmn/hello.bpmn")// 从classpath的资源中加载，一次只能加载一个文件
				.addClasspathResource("bpmn/hello.png")// 从classpath的资源中加载，一次只能加载一个文件
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}

	/**
	 * 部署流程定义（从zip）
	 */
	@Test
	public void deploymentProcessDefinition_zip() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("bpmn/hello.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("流程定义")// 添加部署的名称
				.addZipInputStream(zipInputStream)// 指定zip格式的文件完成部署
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());//
		System.out.println("部署名称：" + deployment.getName());//
	}

	/**
	 * 查询流程定义
	 */
	@Test
	public void findProcessDefinition() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createProcessDefinitionQuery()// 创建一个流程定义的查询
				/** 指定查询条件,where条件 */
				// .deploymentId(deploymentId)//使用部署对象ID查询
				// .processDefinitionId(processDefinitionId)//使用流程定义ID查询
				// .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
				// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
				/** 排序 */
				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
				// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
				/** 返回的结果集 */
				.list();// 返回一个集合列表，封装流程定义
		// .singleResult();//返回惟一结果集
		// .count();//返回结果集数量
		// .listPage(firstResult, maxResults);//分页查询
		if (list != null && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称:" + pd.getName());// 对应hello.bpmn文件中的name属性值
				System.out.println("流程定义的key:" + pd.getKey());// 对应hello.bpmn文件中的id属性值
				System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
				System.out.println("资源名称bpmn文件:" + pd.getResourceName());
				System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
				System.out.println("部署对象ID：" + pd.getDeploymentId());
				System.out.println("*********************************************");
			}
		}
	}

	/**
	 * 删除流程定义
	 */
	@Test
	public void deleteProcessDefinition() {
		// 使用部署ID，完成删除，指定部署对象id为2501删除
		String deploymentId = "2501";
		/**
		 * 不带级联的删除 只能删除没有启动的流程，如果流程启动，就会抛出异常
		 */
//      processEngine.getRepositoryService()//
//                      .deleteDeployment(deploymentId);

		/**
		 * 级联删除 不管流程是否启动，都能可以删除
		 */
		processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		System.out.println("删除成功！");
	}

	/**
	 * 查看流程图
	 *
	 * @throws IOException
	 */
	@Test
	public void viewPic() throws IOException {
		/** 将生成图片放到文件夹下 */
		String deploymentId = "1";
		// 获取图片资源名称
		List<String> list = processEngine.getRepositoryService()
				.getDeploymentResourceNames(deploymentId);
		// 定义图片资源的名称
		String resourceName = "";
		if (list != null && list.size() > 0) {
			for (String name : list) {
				if (name.indexOf(".png") >= 0) {
					resourceName = name;
				}
			}
		}

		// 获取图片的输入流
		InputStream in = processEngine.getRepositoryService()//
				.getResourceAsStream(deploymentId, resourceName);

		// 将图片生成到F盘的目录下
		File file = new File("F:/" + resourceName.substring(resourceName.lastIndexOf(File.separator)));
		FileOutputStream fileOS = new FileOutputStream(file);
		// 将输入流的图片写到磁盘
		int bytesWritten = 0;
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = in.read(bytes)) != -1) {
			fileOS.write(bytes, bytesWritten, byteCount);
			bytesWritten += byteCount;
		}
		in.close();
		fileOS.flush();
		fileOS.close();
	}

}
