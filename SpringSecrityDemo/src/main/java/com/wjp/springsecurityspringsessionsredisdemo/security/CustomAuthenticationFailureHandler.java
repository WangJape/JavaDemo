package com.wjp.springsecurityspringsessionsredisdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证失败处理器
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response,AuthenticationException exception)
            throws IOException, ServletException {

        //HttpSession session = request.getSession();

        // 返回了验证失败的状态码401，并把异常信息以Json返回
        int value = HttpStatus.UNAUTHORIZED.value();
        //response.setStatus(value);
        Map<String, Object> data = new HashMap<>();
        data.put("status", value);
        //System.out.println(exception.getClass());
        if(exception instanceof UsernameNotFoundException){
            data.put("msg", "AccountNotFound");
        }else if(exception instanceof BadCredentialsException){
            data.put("msg", "PasswordMistake");
            //exception.printStackTrace();
        }
        response.getWriter().append(objectMapper.writeValueAsString(data));
    }
}
