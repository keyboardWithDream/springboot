package com.study.cache;

import com.study.cache.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class CacheApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisTemplate<Object, Employee> redisTemplate;

	@Test
	void contextLoads() {
		//操作字符串
		//保存数据
		//stringRedisTemplate.opsForValue().append("msg", "hello");
		//读取数据
		//String msg = stringRedisTemplate.opsForValue().get("msg");
		//System.out.println(msg);

		//操作列表
		//stringRedisTemplate.opsForList().leftPush("myList", "1");
		//stringRedisTemplate.opsForList().leftPush("myList", "2");
		//stringRedisTemplate.opsForList().leftPush("myList", "3");

		Employee employee = new Employee(0, "admin", "123@qq.com", 0, 0);
		redisTemplate.opsForValue().set("emp-1", employee);
	}

}
