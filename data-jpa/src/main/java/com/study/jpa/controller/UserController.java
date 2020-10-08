package com.study.jpa.controller;

import com.study.jpa.dao.UserDao;
import com.study.jpa.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@RestController
public class UserController {

    @Autowired
    private UserDao dao;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id){
        return dao.getOne(id);
    }

    @GetMapping("/user")
    public User insertUser(User user){
        return dao.save(user);
    }
}
