package com.study.web.controller;

import com.study.web.exception.HelloException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Harlan
 * @Date 2020/10/7
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(HelloException.class)
    public Map<String, Object> handleException(Exception e){
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", "hello.notExist");
        map.put("message", e.getMessage());
        return map;
    }
}
