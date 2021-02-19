package com.wjp.springsecurityspringsessionsredisdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 在用户请求处理过程中遇到认证异常时，使用特定认证方式进行认证
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        HashMap<String, String> result = new HashMap<>();
        result.put("status", "401");
        result.put("msg", "未登录，无法访问，请登录后访问");
        response.getWriter().append(objectMapper.writeValueAsString(result));
    }
}
