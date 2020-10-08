# Spring Boot与数据访问

> 对于数据访问层, 无论是SQL还是NOSQL, Spring Boot默认采用整合Spring Data的方式进行同一处理, 添加大量的自动配置, 屏蔽了很多设置. 引入各种xxxTemplate, xxxRepository 来简化我们对数据访问层的操作. 对于我们来说只需要进行简单的设置即可. 

## 整合JDBC

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

使用`datasource`属性, 可以配置关于数据源的连接信息

```yaml
spring:
  datasource:
    username: root
    password: Hhn004460
    url: jdbc:mysql://10.39.2.153:3306/jdbc
    driver-class-name: com.mysql.cj.jdbc.Driver
```

SpringBoot2.x默认使用的`HikariDataSource`作为数据源![dataSource](D:\Code\springboot\data-jdbc\note\images\dataSource.png)

数据源全部相关配置可在`DataSourceProperties`中查看.

SpringBoot默认可以支持`com.zaxxer.hikari.HikariDataSource`和`org.apache.tomcat.jdbc.pool.DataSource`两种数据源.

==自定义数据源类型==: 可在配置文件中使用`type`属性指定数据源

```java
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type")
static class Generic {
    
   @Bean
   public DataSource dataSource(DataSourceProperties properties) {
       //使用DataSourceBuilder创建数据源，利用反射创建响应type的数据源，并且绑定相关属性
      return properties.initializeDataSourceBuilder().build();
   }
    
}
```

---

### 自动创建数据表

`DataSourceInitializer`可以用于自动创建数据表

1. `runSchemaScripts()`方法; 运行建表语句

2. `runDataScripts()`方法; 运行插入语句

   默认只需要将文件命名为: `schema-*.sql` 或 `data-*.sq`

   也可以通过配置指定配置文件

   ```yaml
   schema: 
    - classpath:people.sql
   ```

在SpringBoot2.x以上时需要自动创建数据表需要额外配置以下内容

```yaml
spring:
	datasource:
		initialization-mode: always
```

---

## 整合Druid数据源

### 依赖坐标

```xml
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid</artifactId>
   <version>1.1.23</version>
</dependency>
<!-- 日志依赖 -->
<dependency>
   <groupId>log4j</groupId>
   <artifactId>log4j</artifactId>
   <version>1.2.17</version>
</dependency>
```

### `application`配置

使用`type`属性切换数据源

```yaml
spring:
  datasource:
    username: root
    password: Hhn004460
    url: jdbc:mysql://10.39.2.153:3306/jdbc
    driver-class-name: com.mysql.cj.jdbc.Driver

    # 更改数据源
    type: com.alibaba.druid.pool.DruidDataSource
    # 数据源其他配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```

### Druid 配置类

将Druid注入到IoC容器中

配置监控页面`Servlet`

配置监控的`Filter`

```java
@Configuration
public class DruidConfig {

    /**
     * 将DataSource添加到容器
     * @return Druid
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        return new DruidDataSource();
    }

    
    //配置Druid的监控
    /**
     * 配置一个管理后台的Servlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean <StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>(4);
        //配置登录信息
        initParams.put("loginUsername", "admin");
        //用户名及密码
        initParams.put("loginPassword", "123456");
        //访问权限
        initParams.put("allow", "");
        initParams.put("deny", "");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 配置后台监控Filter
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter(){
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>(1);
        //排除过滤资源
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        bean.setUrlPatterns(List.of("/*"));
        bean.setInitParameters(initParams);
        return bean;
    }
}
```

---

## 整合MyBatis

### 依赖坐标

在项目创建时可添加关于 `MyBatis`, `Mysql`的场景, 并导入关于数据库的驱动, 在`pom`文件中会生成以下依赖坐标

```xml
<!-- mybatis start -->
<dependency>
   <groupId>org.mybatis.spring.boot</groupId>
   <artifactId>mybatis-spring-boot-starter</artifactId>
   <version>2.1.3</version>
</dependency>

<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
   <scope>runtime</scope>
</dependency>
```

### 配置数据源

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: Hhn004460
    url: jdbc:mysql://10.39.2.153:3306/jdbc
```

### 使用注解

#### `@Mapper` 指定操作数据库接口

是定其是一个操作数据库的`Mapper`

```java
@Mapper
public interface DepartmentDao {
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(departmentName) values (#{departmentName})")
    int insertDept(Department department);
    
    ...
}
```

其它使用方式参考MyBatis官方文档

### 自定义MyBatis规则

给容器中添加一个`ConfigurationCustomizer`组件, 重写`customize`中的方法进行规则的添加

```java
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                //开启驼峰式命名
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
```

### 批量扫描

使用`MapperScan`批量扫描所有的`Dao`接口

可以标注在`MyBatis`配置类中或`Application`主类中

```java
@SpringBootApplication
@MapperScan("com.study.mybatis.dao")
public class DataMybatisApplication {

   public static void main(String[] args) {
      SpringApplication.run(DataMybatisApplication.class, args);
   }
}
```

### 使用配置文件

```yaml
mybatis:
	# 指定全局配置文件的位置
	config-location: classpath:mybatis/mybatis-config.xml
	# 指定sql映射文件的位置
	mapper-locations: classpath:mybatis/mapper/*.xml
```

更多使用参照: http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

---

## 整合SpringData JPA

> SpringData 项目的目的是为了简化构建基于Spring框架应用的数据访问技术, 包括非关系数据库, `Map-Reduce` 框架, 云数据服务等等; 另外也包含对关系数据库的访问支持. SpringData为我们提供使用统一的`API`来对数据访问层进行操作.`Spring Data Commons`让我们在使用关系型或非关系型数据访问技术时都基于Spring提供的统一标准, 标准包含了`CRUD`等操作

### 依赖坐标

通过添加`SpingData JPA`场景, 会导入以下坐标

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### 使用`JPC`的注解编写实体类

`@Entiy`: 表示实体类, 其属性和数据表可映射

`@Table`: 指定与那张表进行映射, 默认为类名

`@Id`: 指定主键

`@GeneratedValue`: 主键自增等信息

`@Column(name = "last_Name", length = 50)`: 指定字段名, 默认为属性名

```java
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "last_Name", length = 50)
    private String lastName;
    @Column
    private String email;

    省略getter/setter方法
}
```

### 编写Dao接口

使用`JPA`操作数据库, 其`Dao`接口需要基础`JpaRepository<T, V>`类, 其实现了`CRUD`等方法

泛型`T`为对应的实体类, 泛型`V`为实体类主键的包装类

```java
public interface UserDao extends JpaRepository<User, Integer> {
    ...
}
```

### 自动建表

在配置文件中使用`spring.jpa`属性即可配置`jpa`的相关信息

```yml
spring:
  jpa:
    hibernate:
      # 更新或创建数据表结构
      ddl-auto: update
    # 在控制台显示sql
    show-sql: true
```

在项目启动时, `JPA`则会自动创建表结构

![自动创建表](D:\Code\springboot\data-jdbc\note\images\自动创建表.png)

---

