package com.study.cache.service;

import com.study.cache.domain.Employee;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
public interface EmployeeService {

    /**
     * 通过id查询员工
     * @param id 员工id
     * @return 员工信息
     */
    Employee getEmp(Integer id);

    /**
     * 更新员工
     * @param employee 员工信息
     * @return 员工信息
     */
    Employee updateEmp(Employee employee);


    /**
     * 删除员工
     * @param id 员工id
     */
    void deleteEmp(Integer id);


    /**
     * 通过lastName查询员工
     * @param lastName lastName
     * @return 员工信息
     */
    Employee getEmpByLastName(String lastName);
}
