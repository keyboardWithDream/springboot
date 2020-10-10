package com.study.cache.service.impl;

import com.study.cache.dao.EmployeeDao;
import com.study.cache.domain.Employee;
import com.study.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Cacheable(key = "#id")
    @Override
    public Employee getEmp(Integer id) {
        return employeeDao.getEmpById(id);
    }


    @CachePut(key = "#result.id")
    @Override
    public Employee updateEmp(Employee employee) {
        employeeDao.updateEmp(employee);
        return employee;
    }


    @CacheEvict(key = "#id")
    @Override
    public void deleteEmp(Integer id) {
        employeeDao.deleteEmpById(id);
    }


    @Caching(
            cacheable = {
                   @Cacheable(key = "#lastName")
            },
            put = {
                    @CachePut(key = "#result.id"),
                    @CachePut(key = "#result.email")
            }
    )
    @Override
    public Employee getEmpByLastName(String lastName) {
        return employeeDao.getEmpByLastName(lastName);
    }

}
