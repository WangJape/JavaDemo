package com.wjp.springsecurityspringsessionsredisdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 已经登录,但是权限校验异常处理类
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        //System.out.println(e.toString());
        /*HashMap<String, String> result = new HashMap<>();
        result.put("code", "401");
        result.put("msg", "Unauthorized");*/
        response.getWriter().append(objectMapper.writeValueAsString("未经授权的接口访问"));
    }
}
