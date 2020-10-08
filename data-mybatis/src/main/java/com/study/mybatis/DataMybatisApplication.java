package com.study.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.study.mybatis.dao")
public class DataMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataMybatisApplication.class, args);
	}

}
