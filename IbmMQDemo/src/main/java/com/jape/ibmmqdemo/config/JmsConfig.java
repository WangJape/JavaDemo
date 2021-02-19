package com.jape.ibmmqdemo.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JmsConfig {

    @Value("${project.mq.host:10.7.3.168}")
    private String host;
    @Value("${project.mq.port:1414}")
    private Integer port;
    @Value("${project.mq.queue-manager:QM1}")
    private String queueManager;
    @Value("${project.mq.channel:DEV.ADMIN.SVRCONN}")
    private String channel;
    @Value("${project.mq.username:admin}")
    private String username;
    @Value("${project.mq.password:passw0rd}")
    private String password;
    @Value("${project.mq.receive-timeout:30000}")
    private long receiveTimeout;

    /**
     * 配置连接工厂:
     * CCSID要与连接到的队列管理器一致，Windows下默认为1381，
     * Linux下默认为1208。1208表示UTF-8字符集，建议把队列管理器的CCSID改为1208
     */
    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setHostName(host);
        try {
            mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mqQueueConnectionFactory.setCCSID(1208);
            mqQueueConnectionFactory.setChannel(channel);
            mqQueueConnectionFactory.setPort(port);
            mqQueueConnectionFactory.setQueueManager(queueManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mqQueueConnectionFactory;
    }

    /**
     * 配置连接认证:
     * 如不需要账户密码链接可以跳过此步，直接将mqQueueConnectionFactory注入下一步的缓存连接工厂。
     */
    @Bean
    public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter.setUsername(username);
        userCredentialsConnectionFactoryAdapter.setPassword(password);
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
        return userCredentialsConnectionFactoryAdapter;
    }

    /**
     * 配置缓存连接工厂:
     * 不配置该类则每次与MQ交互都需要重新创建连接，大幅降低速度。
     */
    @Bean
    @Primary
    public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
        cachingConnectionFactory.setSessionCacheSize(500);
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }

    /**
     * 配置事务管理器:
     * 不使用事务可以跳过该步骤。
     * 如需使用事务，可添加注解@EnableTransactionManagement到程序入口类中，事务的具体用法可参考Spring Transaction.
     */
    @Bean
    public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
        return jmsTransactionManager;
    }

    /**
     * 配置JMS模板:
     * JmsTemplate为JmsOperations的接口实现。
     * 重要：不设置setReceiveTimeout时，当队列为空，从队列中取出消息的方法将会一直挂起直到队列内有消息
     */
    @Bean
    public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(receiveTimeout);
        return jmsTemplate;
    }


}
