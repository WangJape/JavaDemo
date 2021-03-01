package com.jape.springlab.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * 获取Spring上下文
 */
@Service
public class ApplicationContextService {

    /**
     * 获取Spring上下文（方法2-直接注入applicationContext）
     */
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 获取Spring上下文（方法3-通过WebApplicationContextUtils获取）
     */
    public ApplicationContext getApplicationContext(ServletContext sc){
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        WebApplicationContext requiredWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        return webApplicationContext;
    }

    public ApplicationContext getApplicationContext(){
        return this.applicationContext;
    }

    public <T> T getBean(Class<T> clazz) {
        T bean = applicationContext.getBean(clazz);
        return bean;
    }

}
