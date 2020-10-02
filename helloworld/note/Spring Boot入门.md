# Spring Boot入门

## 1. Spring Boot简介

>简化Spring应用开发的一个框架
>
>整个Spring技术栈的一个大整合
>
>J2EE开发的一站解决方案

## 2. 微服务

> 微服务是一种架构风格
>
> 一个应用应该是一组小型服务, 可以通过HTTP的方式进行互通
>
> 每一个功能元素最终都是一个可独立替换和独立升级的软件单元
>
> 与微服务相反的是单体应用(ALL IN ONE)  

## 3. Spring Boot HelloWorld

浏览器发送hello请求, 服务器接受请求并处理, 响应Hello World字符串

### 1. 创建一个Maven工程(jar)

---

### 2. 导入spring boot 依赖

```xml
<!-- spring boot 依赖 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
    <relativePath/>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

---

### 3. 编写一个主程序:启动Spring Boot应用

`@SpringBootApplication`注解: 告知SpringBoot这是一个应用

```java
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {
        //启动Spring应用(传入主程序, 和主程序参数)
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}
```

---

### 4. 编写相关的Controller, Service

```java
@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }
}
```

---

### 5. 运行,访问项目

执行`main()`方法

浏览器访问localhost:8080/hello



![springboot-helloworld](D:\Code\springboot\helloworld\note\images\springboot-helloworld.png)

---

### 6. 简化部署

导入插件, 可将应用打包成一个可执行的jar包

```xml
<build>
    <!-- springboot maven 插件 -->
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

使用maven命令 package命令, 完成后存放在target目录下

![springboot-package](D:\Code\springboot\helloworld\note\images\springboot-package.png)

使用java -jar命令进行执行

---

---

## 4. Hello World入门深入

### 1. POM文件

#### 父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
    <relativePath/>
</parent>
```

**它的父项目(真正管理SpringBoot应用李所有的依赖版本)**

```xml
<parent>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-dependencies</artifactId>
   <version>1.5.9.RELEASE</version>
   <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```

**所以以后项目导入依赖坐标不需要写版本信息(没有在dependencies里面管理的依赖则需要声明版本)**

---

#### 导入的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

`spring-boot-starter-web`:

​	`spring-boot-starter`: spring-boot场景启动器; 帮我们导入了`web`模块正常运行所需要的依赖



Spring Boot将所有的功能场景都抽取出来, 做成各个`starters`(启动器), 只需要在项目中映入这些`starter`则相关场景的所有依赖都会导入进来. 要用什么功能就导入什么场景启动器

---

### 2. 主程序类, 主入口类

```java
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {

        //启动Spring应用
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}
```

`@SpringBootApplication`: SpringBoot 应用标注在某个类上, 说明这个类是SpringBoot的主配置类, SpringBoot就应该运行这个类的`main`方法来启动SpringBoot应用

---

`@SpringBootApplication`注解类(组合注解):

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
    ...
}
```

`@SpringBootConfiguration`(SpringBoot配置类): 标注在某个类上, 表示这是一个SpringBoot的配置类.

`@EnableAutoConfiguration`(开启自动配置功能): SpringBoot的自动配置

---

---

## 5. Spring Initializer快速创建Spring Boot项目

IDE都支持使用Spring的项目创建向导快速创建一个SpringBoot项目;

创建项目:

![spring-boot-initializr](D:\Code\springboot\helloworld\note\images\spring-boot-initializr.png)

项目信息:![spring-boot-info](D:\Code\springboot\helloworld\note\images\spring-boot-info.png)

选择场景(功能):![spring-boot-stater](D:\Code\springboot\helloworld\note\images\spring-boot-stater.png)

---

默认生成的SpringBoot项目:

* 主程序已经生成, 只需要编写业务逻辑
* `resource`目录结构
  * `static`: 保存所有的静态资源; js, css, images
  * `templates`: 保存所有的模板页面;(SpringBoot 默认`jar`包使用嵌入式的Tomcat, 默认不支持`jsp`); 可以使用模板引擎(`freemarker`, `thymeleaf`)
  * `application.properties`: SpringBoot 应用的配置文件(可以修改一些默认设置)