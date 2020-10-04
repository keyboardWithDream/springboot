package com.study.config.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/10/3
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello World";
    }
}
