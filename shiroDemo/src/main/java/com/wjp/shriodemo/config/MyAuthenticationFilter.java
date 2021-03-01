package com.wjp.shriodemo.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author: wjp
 * @description: 对没有登录的请求进行拦截, 全部返回json信息. 覆盖掉shiro原本的跳转login.jsp的拦截方式
 * @date:
 */
public class MyAuthenticationFilter extends FormAuthenticationFilter {

	/**
	 * 当接入被拒绝
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		System.err.println("进入用户认证过滤器！");
		try {
			WebUtils.issueRedirect(request,response,"/401");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Bean
	public FilterRegistrationBean registration(MyAuthenticationFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setEnabled(false);
		return registration;
	}
}
