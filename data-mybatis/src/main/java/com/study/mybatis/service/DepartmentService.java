package com.study.mybatis.service;

import com.study.mybatis.domain.Department;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/10/19
 */
public interface DepartmentService {

    /**
     * 查询所有部门
     * @return 部门list
     */
    List<Department> selectAllDept();

    /**
     * 通过id查询部门
     * @param id 部门id
     */
    void getDeptById(Integer id);

    /**
     * 通过id删除部门
     * @param id 部门id
     */
    void deleteDeptById(Integer id);

    /**
     * 插入部门数据
     * @param department 部门信息
     */
    void insertDept(Department department);

    /**
     * 更新部门信息
     * @param department 部门信息
     */
    void updateDept(Department department);
}
