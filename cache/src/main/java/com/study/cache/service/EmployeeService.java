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
}
