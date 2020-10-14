# Spring Boot 与任务

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