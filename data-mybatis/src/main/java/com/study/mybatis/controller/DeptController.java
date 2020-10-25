package com.study.mybatis.controller;

import com.study.mybatis.domain.Department;
import com.study.mybatis.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@Controller
public class DeptController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/dept")
    public String insertDept(Department department, Model model){
        departmentService.insertDept(department);
        model.addAttribute("deptList", departmentService.selectAllDept());
        return "index";
    }

    @PostMapping("/dept/update")
    public String updateDept(Department department, Model model){
        departmentService.updateDept(department);
        model.addAttribute("deptList", departmentService.selectAllDept());
        return "index";
    }

    @GetMapping("/dept")
    public String selectAllDept(Model model){
        model.addAttribute("deptList", departmentService.selectAllDept());
        return "index";
    }

    @PostMapping("/dept/delete")
    public String deleteDeptById(Integer id, Model model){
        departmentService.deleteDeptById(id);
        model.addAttribute("deptList", departmentService.selectAllDept());
        return "index";
    }
}
