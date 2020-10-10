package com.study.cache.dao;

import com.study.cache.domain.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Author Harlan
 * @Date 2020/10/10
 */
@Repository
public interface EmployeeDao {

    /**
     * 通过id查询员工
     * @param id 员工id
     * @return 员工信息
     */
    @Select("select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

    /**
     * 更新员工
     * @param employee 员工信息
     */
    @Update("update employee set lastName=#{lastName}, email=#{email}, gender=#{gender}, d_id=#{dId} where id=#{id}")
    void updateEmp(Employee employee);

    /**
     * 通过id删除员工
     * @param id 员工id
     */
    @Delete("delete from employee where id=#{id}")
    void deleteEmpById(Integer id);

    /**
     * 保存员工
     * @param employee 员工信息
     */
    @Insert("insert into employee(lastName, email, gender, d_id) values (#{lastName}, #{email}, #{gender}, #{dId})")
    void insertEmp(Employee employee);

    /**
     * 通过lastName查询员工
     * @param lastName lastName
     * @return 员工信息
     */
    @Select("select * from employee where lastName=#{lastName}")
    Employee getEmpByLastName(String lastName);
}
