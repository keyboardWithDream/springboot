package com.study.config;

import com.study.config.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot的单元测试
 */
@SpringBootTest
class ConfigApplicationTests {

	@Autowired
	private Person person;

	@Autowired
	private ApplicationContext ac;

	@Test
	void contextLoads() {
		System.out.println(person);
	}

	@Test
	void testHelloService(){
		boolean b = ac.containsBean("helloService");
		System.out.println(b);
	}

}
