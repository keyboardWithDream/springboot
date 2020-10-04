package com.study.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoggingApplicationTests {

	//获取日志记录器
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	void contextLoads() {
		//日志级别
		logger.trace( "这是trace(跟踪)日志...");
		logger.debug("这是debug(调试)日志...");
		//SpringBoot默认给我们使用的是info级别
		logger.info("这是info(普通)日志...");
		logger.warn("这是warn(警告)日志...");
		logger.error("这是error(错误)日志...");
	}

}
