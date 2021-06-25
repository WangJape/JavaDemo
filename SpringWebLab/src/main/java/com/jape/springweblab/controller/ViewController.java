package com.jape.springweblab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @GetMapping("getChatOnlineView")
    public String getChatOnlineView(){
        return "chatOnLine";
    }

    @RequestMapping("getVueAxiosView")
    public String getRolePermissionWithPermissionListView(){
        return "vueAxios";
    }

}
