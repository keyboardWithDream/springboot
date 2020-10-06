package com.study.web.controller;

import com.study.web.dao.DepartmentDao;
import com.study.web.dao.EmployeeDao;
import com.study.web.domian.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

/**
 * @Author Harlan
 * @Date 2020/10/6
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;


    /**
     * 查询所有员工
     * @return 列表页面
     */
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    /**
     * 跳转员工添加页面
     * @return 员工添加页面
     */
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //查出所有的部门在页面中显示
        model.addAttribute("depts", departmentDao.getDepartments());
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }
}
