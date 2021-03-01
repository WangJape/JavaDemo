package com.wjp.shriodemo.config;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: wjp
 * @description: shiro统一异常拦截处理器
 * @date:
 */
@ControllerAdvice
@ResponseBody
public class ShiroExceptionHandler {

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public String unauthenticatedException() {
        return "未登录";
    }

	/**
	 * 权限不足报错拦截
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public String unauthorizedExceptionHandler() {
		return "权限不足";
	}
}
