package com.wjp.springsecurityspringsessionsredisdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjp.springsecurityspringsessionsredisdemo.entity.VueRouter;
import com.wjp.springsecurityspringsessionsredisdemo.service.VueRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功后的处理类
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VueRouterService vueRouterService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,Authentication authentication)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "LoginSuccess");
        ArrayList<VueRouter>  list = vueRouterService.getUserRoleAllVueRouter(request.getParameter("userAccount"));
        map.put("vuerouter",list);

        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.put("getDetails()", auth.getDetails());
        map.put("getAuthorities()",auth.getAuthorities());
        map.put("getPrincipal()",auth.getPrincipal());*/
        //System.err.println(user.getUsername());
        //System.err.println(user.getPassword());
        //System.err.println(user.getAuthorities().toArray());
        System.err.println(request.getParameter("userAccount") + "登陆成功");
        session.setAttribute("userAccount",request.getParameter("userAccount"));
        session.setAttribute("usertype",request.getParameter("userType"));
        // 登录成功后，返回json数据
        response.getWriter().append(objectMapper.writeValueAsString(map));
    }

}
