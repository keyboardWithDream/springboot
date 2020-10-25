package com.study.mybatis.service.impl;

import com.study.mybatis.dao.DepartmentDao;
import com.study.mybatis.domain.Department;
import com.study.mybatis.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/10/19
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> selectAllDept() {
        return departmentDao.selectAllDept();
    }

    @Override
    public void getDeptById(Integer id) {
        departmentDao.getDeptById(id);
    }

    @Override
    public void deleteDeptById(Integer id) {
        departmentDao.deleteDeptById(id);
    }

    @Override
    public void insertDept(Department department) {
        departmentDao.insertDept(department);
    }

    @Override
    public void updateDept(Department department) {
        departmentDao.updateDept(department);
    }
}
