package com.study.data.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@Controller
public class HelloController {

    @Autowired
    private JdbcTemplate template;

    @ResponseBody
    @GetMapping("/query")
    public Map<String, Object> hello(){
        List<Map<String, Object>> mapList = template.queryForList("select * from department");
        return mapList.get(0);
    }
}
