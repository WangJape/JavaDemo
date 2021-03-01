package com.wjp.shriodemo.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //访问数据库的工具
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    //SecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm());
        //defaultWebSecurityManager.setCacheManager(cacheManager());
        //defaultWebSecurityManager.setSessionManager(defaultWebSessionManager());
        return defaultWebSecurityManager;
    }

    //ShiroFilterFactoryBean
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        //设置登录请求路径
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("authc", new MyAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        /**
         * 请求拦截过滤链
         * anon :所有url都都可以匿名访问，无需认证
         * authc:所有url都必须认证通过才可以访问
         * user :必须拥有记住我（remember me）才能访问
         * perms:拥有对某个资源的权限才能访问
         * role :拥有每个角色权限才能访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");
        //filterChainDefinitionMap.put("/401", "anon");
        //filterChainDefinitionMap.put("/doLogin", "anon");
        //filterChainDefinitionMap.put("/api/**", "anon");
        //filterChainDefinitionMap.put("/admin", "perms[admin]");
        //filterChainDefinitionMap.put("/superadmin", "perms[superadmin]");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        //filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        System.err.println(shiroFilterFactoryBean.getFilters());
        System.err.println(shiroFilterFactoryBean.getFilterChainDefinitionMap());

        return shiroFilterFactoryBean;

    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions)
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getDefaultWebSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
