package com.study.mybatis.controller;

import com.study.mybatis.dao.DepartmentDao;
import com.study.mybatis.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@RestController
public class DeptController {

    @Autowired
    private DepartmentDao deptDao;

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return deptDao.getDeptById(id);
    }

    @GetMapping("/dept")
    public Integer insertDept(Department department){
        return deptDao.insertDept(department);
    }
}
