#  Spring Boot 与任务

## 异步任务

在springboot中, 如果需要使用多线程, 开启异步任务 需要在方法上添加`@Async`注解, 并在主类上使用`@EnableAsync`开启异步支持

异步方法:

```java
@Service
public class AsyncService {
    /**
     * 告诉Spring这是一个异步方法
     */
    @Async
    public void hello(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中...");
    }
}
```

主配置类:

```java
@EnableAsync
@SpringBootApplication
public class TaskApplication {
   public static void main(String[] args) {
      SpringApplication.run(TaskApplication.class, args);
   }
}
```

---

## 定时任务

> 项目开发中经常需要执行一些定时任务, 比如需要在每天凌晨分析前一天的日志信息, Spring为我们提供了异步执行任务调度的方式, 提供`TaskExecutor`, `TaskScheduler` 接口

使用`@Scheduled`注解在方法上设置定时任务

`cron`属性可设置定时表达式: `second(秒) minute(分) hour(时) day(日) month(月) weekdays(周几)`

| 字段 | 允许值                       | 允许的特殊符号                    |
| ---- | ---------------------------- | --------------------------------- |
| 秒   | 0-59                         | `,`  `-`  `*`  `/`                |
| 分   | 0-59                         | `,`  `-`  `*`  `/`                |
| 小时 | 0-23                         | `,`  `-`  `*`  `/`                |
| 日期 | 1-31                         | `,`  `-`  `*`  `/`  `L`  `W`  `C` |
| 月份 | 1-12                         | `,`  `-`  `*`  `/`                |
| 星期 | 0-7 或 SUN-SAT (0, 7 是 SUN) | `,`  `-`  `*`  `/`  `L`  `W`  `C` |

特殊字符含义表:

| 特殊字符 | 代表含义                   |
| -------- | -------------------------- |
| `,`      | 枚举                       |
| `-`      | 区间                       |
| `*`      | 任意                       |
| `/`      | 步长                       |
| `?`      | 日/星期 冲突匹配           |
| `L`      | 最后                       |
| `W`      | 工作日                     |
| `C`      | 和calendar联系后计算过的值 |
| `#`      | 星期, 4#2 (第2个星期四)    |

---

eg:

1. `* * * * * MON-SAT` :  周一到周六的每秒
2. `0,1,2,3,4 * * * * 0-6` : 每个(0, 1, 2, 3, 4, 5)秒运行 - 枚举
3. `0-29 * * * * 0-6` : 每个0-29秒运行
4. `0/4 * * * * 0-6` : 0秒启动, 每4秒执行一次

---

```java
@Service
public class ScheduleService {

    @Scheduled(cron = "* * * * * 0-6")
    public void hello(){
        System.out.println("正在执行定时任务...");
    }
}
```

在主类中使用`@EnableScheduling`开启定时任务的支持

```java
@EnableScheduling
@SpringBootApplication
public class TaskApplication {

   public static void main(String[] args) {
      SpringApplication.run(TaskApplication.class, args);
   }
}
```

---

## 邮件任务

### 引入依赖坐标

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### 配置邮箱

```yaml
spring:
  mail:
    host: smtp.163.com
    username: i102443@163.com
    password: ROCBSS----ZBPXZP
    properties: 
      mail: 
        smtp:
          ssl:
            enable: true
```

### 邮件测试

高级邮件需要`MimeMessageHelper`进行设置

```java
@SpringBootTest
class TaskApplicationTests {

   @Autowired
   JavaMailSender mailSender;

    /**
    * 简单邮件
    */
   @Test
   void contextLoads() {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setSubject("通知: 这是一个邮件测试!");
      message.setText("您好!彭小川先生,这是一个SpringBoot的邮件任务测试,收到后请勿回复,谢谢!");
      message.setTo("765403293@qq.com");
      message.setFrom("i102443@163.com");
      mailSender.send(message);
   }
    
    
    /**
	 * 高级邮件
	 * @throws MessagingException 异常
	 */
	@Test
	public void sendMail() throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		messageHelper.setSubject("通知: 这是一个高级邮件测试!");
		messageHelper.setText("<h1>这是一个高级邮件测试!</h1>", true);
		messageHelper.setTo("1353662613@qq.com");
		messageHelper.setFrom("i102443@163.com");
		messageHelper.addAttachment("test.png", new File("C:/Users/13536/Desktop/kanye west.png"));
		mailSender.send(message);
	}

}
```