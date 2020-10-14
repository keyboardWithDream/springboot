# Spring Boot 与安全

> 应用程序的两个主要区域是"认证"和"授权"(访问控制). 这两个主要区域是Spring Security 的两个目标
>
> "认证" (Authentication), 是建立一个他声明的主体的过程(一个"主体"一般是指用户, 设备或一些可以在你的应用程序中执行行动的其他系统).
>
> "授权"(Authorization), 指确定一个主体是否允许在你的应用程序执行一个动作的过程. 为了抵达需要授权的店, 主体的身份已经有认证过程建立
>
> 这个概念不只是在Spring Security中体现.

## 引入Spring Security

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## 编写Spring Security配置类

