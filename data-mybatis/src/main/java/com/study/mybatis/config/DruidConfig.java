package com.study.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Harlan
 * @Date 2020/10/8
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> servlet = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> paramsMap = new HashMap<>(4);
        paramsMap.put("loginUsername", "admin");
        paramsMap.put("loginPassword", "123456");
        paramsMap.put("allow", "");
        paramsMap.put("deny", "");
        servlet.setInitParameters(paramsMap);
        return servlet;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilterFilter(){
        FilterRegistrationBean<WebStatFilter> filter = new FilterRegistrationBean<>(new WebStatFilter());
        Map<String, String> paramsMap = new HashMap<>(1);
        paramsMap.put("exclusions", "*.js,*.css,/druid/*");
        filter.setUrlPatterns(Collections.singleton("/*"));
        filter.setInitParameters(paramsMap);
        return filter;
    }
}
