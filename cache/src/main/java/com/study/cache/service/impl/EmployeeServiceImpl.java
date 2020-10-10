package com.study.cache.service.impl;

import com.study.cache.dao.EmployeeDao;
import com.study.cache.domain.Employee;
import com.study.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Cacheable(cacheNames = {"emp"})
    @Override
    public Employee getEmp(Integer id) {
        return employeeDao.getEmpById(id);
    }
}
