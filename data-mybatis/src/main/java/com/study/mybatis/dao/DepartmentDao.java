package com.study.mybatis.dao;

import com.study.mybatis.domain.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@Repository
public interface DepartmentDao {

    /**
     * 查询所有部门
     * @return 部门list
     */
    @Select("select * from department")
    List<Department> selectAllDept();

    /**
     * 通过id查询部门
     * @param id 部门id
     */
    @Select("select * from department where id = #{id}")
    void getDeptById(Integer id);

    /**
     * 通过id删除部门
     * @param id 部门id
     */
    @Delete("delete from department where id = #{id}")
    void deleteDeptById(Integer id);

    /**
     * 插入部门数据
     * @param department 部门信息
     */
    @Insert("insert into department values (#{id}, #{departmentName})")
    void insertDept(Department department);

    /**
     * 更新部门信息
     * @param department 部门信息
     */
    @Update("update department set departmentName=#{departmentName} where id = #{id}")
    void updateDept(Department department);
}
