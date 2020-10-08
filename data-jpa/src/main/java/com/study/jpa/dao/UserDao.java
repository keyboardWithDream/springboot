package com.study.jpa.dao;

import com.study.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
public interface UserDao extends JpaRepository<User, Integer> {
}
