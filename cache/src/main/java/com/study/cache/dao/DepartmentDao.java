package com.study.cache.dao;

import com.study.cache.domain.Department;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
@Repository
public interface DepartmentDao {

    /**
     * 通过id查询部门
     * @param id 部门id
     * @return 部门信息
     */
    @Select("select * from department where id=#{id}")
    Department getDeptById(Integer id);
}
