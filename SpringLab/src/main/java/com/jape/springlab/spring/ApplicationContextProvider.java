package com.jape.springlab.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取Spring上下文(方法1-实现ApplicationContextAware接口，重写setApplicationContext方法)
 *
 * 使用ApplicationContext来生成及管理Bean实例的话，
 * 在执行BeanFactoryAware的setBeanFactory()阶段后，
 * 若Bean类上有实现org.springframework.context.ApplicationContextAware接口，
 * 则执行其setApplicationContext()方法，
 * 接着才执行BeanPostProcessors的ProcessBeforeInitialization()及之后的流程。
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    /**
     * Spring上下文 实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
