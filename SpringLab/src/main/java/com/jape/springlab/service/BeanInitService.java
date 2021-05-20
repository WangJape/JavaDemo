package com.jape.springlab.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Bean初始化Demo
 *
 * @author Jape
 * @since 2021/4/19 13:48
 */
@Service
public class BeanInitService implements InitializingBean {

    @Autowired
    private RoleService roleService;

    BeanInitService() {
        System.err.println("Construct" + roleService);
    }

    /**
     * 在@Autowired注入之后执行
     */
    @PostConstruct
    public void init() {
        System.err.println("PostConstruct" + roleService);
    }

    /**
     * InitializingBean在@PostConstruct之后执行
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("InitializingBean" + roleService);
    }
}
