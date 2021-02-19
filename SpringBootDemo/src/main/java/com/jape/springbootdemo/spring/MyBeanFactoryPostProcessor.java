package com.jape.springbootdemo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 可以更改beanDefinition
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("adminService");
        //修改属性name值
        //beanDefinition.getPropertyValues().add("name", "liSi");
        System.err.println(configurableListableBeanFactory);
    }
}
