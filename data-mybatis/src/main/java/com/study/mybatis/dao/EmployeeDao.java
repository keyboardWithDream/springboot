package com.study.mybatis.dao;

import com.study.mybatis.domain.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@Mapper
public interface EmployeeDao {

    /**
     * 通过id查询员工
     * @param id 员工id
     * @return 员工实体类
     */
    @Select("select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

}
