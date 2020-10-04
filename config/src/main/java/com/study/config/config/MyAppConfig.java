package com.study.config.config;

import com.study.config.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration:指明当前类是一个配置类, 就是来替代之前的Spring配置文件
 * 在配置文件中使用<bean>标签添加组件
 * @Author Harlan
 * @Date 2020/10/3
 */
@Configuration
public class MyAppConfig {

    /**
     * 将方法的返回值添加到容器中
     * @return helloService
     */
    @Bean
    public HelloService helloService(){
        return new HelloService();
    }
}
