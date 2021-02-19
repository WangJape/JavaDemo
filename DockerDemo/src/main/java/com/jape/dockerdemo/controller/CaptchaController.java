package com.jape.dockerdemo.controller;

import com.jape.dockerdemo.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 验证码
 * @Auther: 王建朋
 * @Date: 2020/9/9 21:06
 */
@RestController
public class CaptchaController {

    @GetMapping("getCaptchaImg")
    public void getCaptchaImg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        CaptchaUtil captchaUtil = new CaptchaUtil();

        captchaUtil.write(response.getOutputStream());

    }

}
