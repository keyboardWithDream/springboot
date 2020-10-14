package com.study.task.controller;

import com.study.task.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/10/14
 */
@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/hello")
    public String hello(){
        asyncService.hello();
        return "success!";
    }
}
