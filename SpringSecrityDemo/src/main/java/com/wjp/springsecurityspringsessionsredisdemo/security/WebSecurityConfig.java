package com.wjp.springsecurityspringsessionsredisdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * prePostEnabled = true
 * @PreAuthorize 方法调用之前,表达式来计算是否可以授权访问
 * @PostAuthorize 标记的方法调用之后, 表达式来计算是否可以授权访问
 * @PreFilter 基于方法入参相关的表达式, 对入参进行过滤
 * @PostFilter 基于返回值相关的表达式，对返回值进行过滤
 *
 * securedEnabled = true
 * @Secured 该注解功能要简单的多，默认情况下只能基于角色（默认需要带前缀 ROLE_）集合来进行访问控制决策
 *
 * jsr250Enabled = true
 * @DenyAll 拒绝所有的访问
 * @PermitAll 同意所有的访问
 * @RolesAllowed 用法和 上边中的 @Secured 一样。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/", "/test1").permitAll()
                //.antMatchers("/admin").hasRole("admin")
                //.antMatchers("/superadmin").hasRole("superadmin")
                .anyRequest().permitAll(); //没办法，我想用注解，又不想把公共的列在上边，只能这么干了，谁叫他请求进来先过这个，符合这个才过注释呢
                //.anyRequest().anonymous();
                //.anyRequest().authenticated();
        http
                .formLogin()
                .usernameParameter("userAccount")
                .passwordParameter("userPassword")
                .loginProcessingUrl("/doLogin")
                //.loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
                //.permitAll();

        http
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        http
                .logout()
                .logoutUrl("/doLogout")
                .logoutSuccessHandler(customLogoutSuccessHandler);
                //.permitAll();
        http
                .rememberMe()
                .rememberMeParameter("rememberMe");

        // 关闭CSRF跨域
        http.csrf().disable();
}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("jape").password(new BCryptPasswordEncoder().encode("970501")).roles("superadmin","admin")
                .and()
                .withUser("wei").password(new BCryptPasswordEncoder().encode("182320")).roles("admin");*/
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    /**
     * 由于SpringSecurity先于Spring加载，导致无法注入数据库server
     * 所以让Spring管理加载这个bean
     * @return
     */
    @Bean
    public MyUserDetailsService myUserDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(myUserDetailsService());
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
}