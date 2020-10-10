package com.study.cache;

import com.study.cache.dao.EmployeeDao;
import com.study.cache.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheApplicationTests {

	@Autowired
	EmployeeDao employeeDao;

	@Test
	void contextLoads() {
		Employee emp = employeeDao.getEmpById(1);
		System.out.println(emp);
	}

}
