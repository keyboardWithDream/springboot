package com.study.mybatis.dao;

import com.study.mybatis.domain.Department;
import org.apache.ibatis.annotations.*;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@Mapper
public interface DepartmentDao {

    /**
     * 通过id查询部门
     * @param id 部门id
     * @return 部门实体类
     */
    @Select("select * from department where id = #{id}")
    Department getDeptById(Integer id);

    /**
     * 通过id删除部门
     * @param id 部门id
     * @return 影响行数
     */
    @Delete("delete from department where id = #{id}")
    int deleteDeptById(Integer id);

    /**
     * 插入部门数据
     * @param department 部门信息
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(departmentName) values (#{departmentName})")
    int insertDept(Department department);

    /**
     * 更新部门信息
     * @param department 部门信息
     * @return 影响行数
     */
    @Update("update department set departmentName=#{departmentName} where id = #{id}")
    int updateDept(Department department);
}
