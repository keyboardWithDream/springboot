package com.study.elasticsearch.domian;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author Harlan
 * @Date 2020/10/25
 */
@Component
public class UserInfo implements Serializable {

    private String name;
    private Integer age;

    public UserInfo() {
    }

    public UserInfo(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
