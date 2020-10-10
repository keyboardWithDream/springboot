package com.study.cache.service.impl;

import com.study.cache.dao.DepartmentDao;
import com.study.cache.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
@CacheConfig(cacheNames = "dept")
@Service
public class DepartmentServiceImpl {

    @Autowired
    private DepartmentDao departmentDao;

    @Cacheable(key = "#id")
    public Department getDeptById(Integer id){
        return departmentDao.getDeptById(id);
    }
}
