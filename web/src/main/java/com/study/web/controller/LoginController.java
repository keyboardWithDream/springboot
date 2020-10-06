package com.study.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author Harlan
 * @Date 2020/10/5
 */
@Controller
public class LoginController {

    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map, HttpSession session){
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            //登录成功
            if(username.equals("admin") && password.equals("123")){
                //放置表单重复提交,使用重定向
                session.setAttribute("loginUser", username);
                return "redirect:/main.html";
            }else {
                map.put("msg", "用户名或密码错误!");
                return "login";
            }
        }else {
            //登录失败
            map.put("msg", "用户名或密码错误!");
            return "login";
        }
    }
}
