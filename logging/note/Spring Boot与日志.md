# Spring Boot与日志

## 一. 日志框架

**市面上的日志框架**: JUL, JCL, Jboss-logging, logback, log4j, log4j2, slf4j ...

|                           日志门面                           |                        日志实现                        |
| :----------------------------------------------------------: | :----------------------------------------------------: |
| `JCL`(jakarta Commons Logging), `SLF4j`(Simple Logging Facade For Java), `Jboss-logging` | `Log4j`, `JUL`(java.util.logging), `Log4j2`, `Logback` |

左边选一个门面(抽象层), 右边选一个实现;

Spring框架默认选择:`JCL` 和 `commmons-logging`

==SpringBoot默认选用:`SLF4J`和`logback`==

---

## 二. SLF4j使用

### 1. 如何在系统中使用SLF4j

在开发的时候, 日志记录方法的调用, 不应该来直接调用日志的实现类, 而是调用日志抽象层里面的方法;

给系统导入`slf4j`的`jar`包 和 `logback`的实现`jar`

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

调用层级关系:

![concrete-bindings](D:\Code\springboot\logging\note\images\concrete-bindings.png)

每一个日志的实现框架都有自己的配置文件, 使用`slf4j`以后, ==**配置文件还是做成日志实现框架的配置文件**==.

### 2. 统一日志框架

A系统(`slf4j` + `logback`) : Spring(`commons-logging`) + Hibernate(`jboss-logging`) + MyBatis

解决同一日志记录, 即是别的框架和系统一起同一使用`slf4j`进行输出

![legacy](D:\Code\springboot\logging\note\images\legacy.png)

**解决方案**:

1. ==将系统中其它日志框架先排除==

2. ==使用中间包来替换原有的日志框架==

3. ==导入`slf4j`其它的实现==
---

## 三. SpringBoot日志关系

SpringBoot使用`spring-boot-starter-logging`来做日志功能

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

**底层依赖关系**:

![dependencies](D:\Code\springboot\logging\note\images\dependencies.png)

==总结:==

1. ==SpringBoot底层也是使用`slf4j` + `logback` 的方式进行日志记录==
2. ==SpringBoot也把其它的日志都替换成立`slf4j`==
3. ==中间的替换包内部使用`slf4j`的实现==
4. ==当引用了其它框架, 一定要把这个框架的默认日志依赖移除掉==

---

## 四. 日志使用

### 1. 默认配置

SpringBoot默认帮我们配置好了日志

```java
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
```

SpringBoot默认只显示`info`级别或以上的日志信息, 可以通过修改配置文件的参数设置显示日志的级别

`logging.level` 包名 = 级别, 默认级别为`root`

```properties
logging.level.com.study=trace
```

---

同时SpringBoot默认只在控制台中输出日志信息, 如果想保存文件需要设置日志路径和文件名

```properties
# 指定日志文件路径和文件名
logging.file.path=/spring/log
logging.file.name=springboot.log
```

即在`linux`的根目录下`/spring/log`下的`springboot.log`文件

---

可以通过配置指定日志输出的格式

```properties
# 在控制台输出的日志格式
logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
# 在日志文件中的输出格式
logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} === %msg%n
```

### 2. 指定配置

给类路径下放上每个日志框架自己的配置文件即可, SpringBoot就不再使用其默认配置

| Logging System         | Customization                                                |
| ---------------------- | ------------------------------------------------------------ |
| Logback                | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`, `logback.groovy` |
| Log4j2                 | `log4j2-spring.xml`, `log4j2.xml`                            |
| JDK(Java.Util.Logging) | `logging.properties`                                         |

