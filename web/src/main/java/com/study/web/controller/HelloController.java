package com.study.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author Harlan
 * @Date 2020/10/4
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    /**
     * 在页面上展示数据
     * @return 返回页面
     */
    @RequestMapping("/success")
    public String success(Map<String, Object> map){
        map.put("msg", "<h1>你好!</h1>");
        map.put("users", Arrays.asList("admin", "harlan", "hhn"));
        return "success";
    }
}
