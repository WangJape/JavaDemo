package com.jape.esjobdemo.config;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.jape.esjobdemo.job.BatchInsertJob;
import com.jape.esjobdemo.job.FileWriterJob;
import com.jape.esjobdemo.job.MySimpleJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ElasticJob配置类
 *
 * @author Jape
 * @since 2020/11/13 23:17
 */
@Configuration
public class ElasticJobConfig {

    @Value("${elasticJob.namespace}")
    private String namespace;
    @Value("${elasticJob.serverLists}")
    private String serverLists;

    /**
     * Zookeeper注册中心配置
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverLists, namespace);
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }


    @Bean
    public MySimpleJob stockJob1() {
        return new MySimpleJob();
    }
    /*@Bean(initMethod = "init")
    public JobScheduler mySimpleJob(ZookeeperRegistryCenter registryCenter, MySimpleJob mySimpleJob) {
        return new SpringJobScheduler(mySimpleJob, registryCenter, getLiteJobConfiguration(
                mySimpleJob.getClass(), "0/2 * * * * ?", 1, "1=1", ""));
    }*/


    @Bean
    public BatchInsertJob stockJob() {
        return new BatchInsertJob();
    }
    /*@Bean(initMethod = "init")
    public JobScheduler batchInsertJob(ZookeeperRegistryCenter registryCenter, BatchInsertJob batchInsertJob) {
        return new SpringJobScheduler(batchInsertJob, registryCenter, getLiteJobConfiguration(
                batchInsertJob.getClass(), "0/2 * * * * ?", 1, "1=1", ""));
    }*/

    @Bean
    public FileWriterJob stockJob2() {
        return new FileWriterJob();
    }
    @Bean(initMethod = "init")
    public JobScheduler fileWriterJob(ZookeeperRegistryCenter registryCenter, FileWriterJob fileWriterJob) {
        return new SpringJobScheduler(fileWriterJob, registryCenter, getLiteJobConfiguration(
                fileWriterJob.getClass(), "0/2 * * * * ?", 1, "1=1", ""));
    }

    /**
     * 配置任务详细信息
     *
     * @param jobClass               任务执行类
     * @param cron                   执行策略
     * @param shardingTotalCount     分片数量
     * @param shardingItemParameters 分片个性化参数
     * @param jobParameters          公共参数
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters,
                                                         final String jobParameters) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration
                .newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .jobParameter(jobParameters)
                .build();
        // 定义Simple类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, jobClass.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration
                .newBuilder(simpleJobConfig)
                .overwrite(true)
                .build();
        return simpleJobRootConfig;
    }

    /**
     * 创建支持dataFlow类型的作业的配置信息
     */
    private LiteJobConfiguration createFlowJobConfiguration(final Class<? extends ElasticJob> jobClass,
                                                            final String cron,
                                                            final int shardingTotalCount,
                                                            final String shardingItemParameters) {
        JobCoreConfiguration jobCoreConfig = JobCoreConfiguration
                .newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .build();
        // 定义数据流类型任务配置
        DataflowJobConfiguration jobConfig = new DataflowJobConfiguration(jobCoreConfig, jobClass.getCanonicalName(), true);
        //创建LiteJobConfiguration
        LiteJobConfiguration liteJobConfig = LiteJobConfiguration
                .newBuilder(jobConfig)
                .overwrite(true)
                // .monitorPort(9888)//设置dump端口
                .build();
        return liteJobConfig;
    }
}
