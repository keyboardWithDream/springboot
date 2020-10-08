# Spring Boot与数据访问

> 对于数据访问层, 无论是SQL还是NOSQL, Spring Boot默认采用整合Spring Data的方式进行同一处理, 添加大量的自动配置, 屏蔽了很多设置. 引入各种xxxTemplate, xxxRepository 来简化我们对数据访问层的操作. 对于我们来说只需要进行简单的设置即可. 

## JDBC

### 导入依赖坐标

在项目创建时可添加关于JDBC的场景, 并导入关于数据库的驱动, 在`pom`文件中会生成以下依赖坐标

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-jdbc</artifactId>
	</dependency>

	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>
</dependencies>
```

### 配置数据库连接信息

使用`datasource`属性, 可以配置连接池的连接信息

```yaml
spring:
  datasource:
    username: root
    password: Hhn004460
    url: jdbc:mysql://10.39.2.153:3306/jdbc
    driver-class-name: com.mysql.cj.jdbc.Driver
```

自动创建数据表