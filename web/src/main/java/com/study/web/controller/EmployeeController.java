package com.study.web.controller;

import com.study.web.dao.DepartmentDao;
import com.study.web.dao.EmployeeDao;
import com.study.web.domian.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加员工信息
     * @param employee 员工信息
     * @return 查询页面
     */
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }


    /**
     * 通过id查询员工并返回到修改页面
     * @param id 员工id
     * @param model 返回员工信息
     * @return 修改页面
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model){
        model.addAttribute("emp", employeeDao.get(id));
        model.addAttribute("depts", departmentDao.getDepartments());
        //返回修改页面
        return "emp/add";
    }


    /**
     * 修改用户
     * @param employee 用户信息
     * @return 查询页面
     */
    @PutMapping("/emp")
    public String updateEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }


    /**
     * 员工删除
     * @param id 员工id
     * @return 员工查询页面
     */
    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
