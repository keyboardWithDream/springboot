package com.study.cache.controller;

import com.study.cache.domain.Employee;
import com.study.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){
        return employeeService.getEmp(id);
    }

    @GetMapping("/emp")
    public Employee updateEmp(Employee employee){
        return employeeService.updateEmp(employee);
    }

    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
        return "WELL DONE!";
    }

    @GetMapping("/emp/l/{lastName}")
    public Employee getEmpByLsatName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }
}
