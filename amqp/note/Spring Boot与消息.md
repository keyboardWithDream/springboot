# Spring Boot与消息

> 大多应用中, 可通过消息服务中间件来提升系统异步通信, 扩展解耦能力
>
> 消息服务中有<font color=red>消息代理(message broker)</font> 和 <font color=red>目的地(destination)</font> 两个重要概念
>
> 当消息发送者发送消息以后, 将由消息代理接管, 消息代理保证消息传递到指定目的地

## 消息队列

>消息队列主要有两种形式的目的地
>
>1. <font color=red>队列(queue)</font>: 点对点消息通信 (point-to-point)
>2. <font color=red>主题(topic)</font>: 发布(publish) / 订阅(subscribe) 消息通信 

### 点对点式

概念: 消息发送者发送消息, 消息代理将其放入一个队列中, 消息接收者从队列中获取消息内容, 消息读取后被移出队列；

消息只有唯一的发送者和接收者, 但并不是说只有一个接收者, 当一条消息被其中一个接收者接收后， 其余接收者则接收不到该消息。

### 发布订阅式

概念: 发送者(发布者) 发送消息到主题, 多个接收者(订阅者) 监听(订阅)这个主题, 那么就会在消息到达时同时收到消息

---

## 消息服务规范

### JMS

Java Message Service(Java 消息服务): 基于JVM消息代理的规范, ActiveMQ, HometMQ 是JMS实现。

### AMQP

Advanced Message Queuing Protocol(高级消息队列协议) 兼容JMS, RabbitMQ 是AMQP的实现。

---

## Spring 支持

`spring-jms`提供了对JMS的支持

`spring-rabbit`提供了对AMQP的支持

需要`ConnectionFactory`的实现来连接消息代理

`@JmsListener`(JMS) / `@RabbitLisrener`(AMQP) 注解在方法上监听消息代理发布的消息

`@EnableJms` / `@EnableRabbit` 开启支持

---

## RabbitMQ

> RabbitMQ是一个由erlang开发的AMQP的开源实现。

### 核心概念

#### <font color=red>Message</font>

消息, 消息是不具名的, 它由消息头和消息体组成. 消息体是不透明的, 而消息头则由一系列的可选属性组成, 这些属性包括`routing-key`(路由键), `priority`(相对于其他消息的优先权), `delivery-mode`(指出该消息可能需要持久性存储)等..

#### <font color=red>Publisher</font>

消息的生产者, 也是一个向交换器发布消息的客户端应用程序

#### <font color=red>Exchange</font>

交换器, 用来接收生产者发送的消息并将这些消息路由给服务器中的队列.

**Exchange有4种类型**: `direct`(默认: 可实现点对点), `fanout`, `topic`, 和 `headers`, 不同的类型的Exchange转发消息的策略有所区别

#### <font color=red>Queue</font>

消息队列, 用来保存消息直到发送给消费者. 它是消息的容器, 也是消息的终点. 一个消息可投入一个或多个队列. 消息一直在队列里面, 等待消费者连接到这个队列将其取走.

#### <font color=red>Binding</font>

绑定, 用于消息队列的交换器之间的关联. 一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则, 所以可以将交换器理解成一个由绑定构成的路由表.

==Exchange 和 Queue 的绑定可以是多对多的关系==

#### <font color=red>Connection</font>

网络连接, 比如一个TCP连接

#### <font color=red>Channel</font>

信道, 多路复用连接中的一条独立的双向数据流通道. 信道是建立在真实的TCP连接内的虚拟连接, AMQP命令都是通过信道发出去的, 不管是发布消息, 订阅队列, 还是接收消息, 这些动作都是通过信道完成. 因为对于操作系统来说建立和销毁TCP都是非常昂贵的开销, 所以引入了信道的概念, 以复用一条TCP连接

#### <font color=red>Consumer</font>

消息的消费者, 表示一个从消息队列中取得消息的客户端应用程序

#### <font color=red>Virtual Host</font>

虚拟主机, 表示一批交换器, 消息队列 和相关对象. 虚拟主机是共享相同的身份认证和加密环境的独立服务器域.

每个`vhost`本质上是一个mini版的RabbitMQ服务器, 拥有自己的队列, 交换器, 绑定, 和权限机制. `vhost`是AMQP概念的基础, 必须在连接时指定, RabbitMQ默认的`vhost`是`/`.

#### <font color=red>Broler</font>

表示消息队列服务器实体

---

**流程图:**

![MQ流程](images\MQ流程.png)

---

### 运行机制

#### AMQP中的消息路由

AMQP中消息路由过程和Java开发者属性的JMS存在一些差别, AMQP中增加了`Exchange`和`Binding`的角色. 生产者把消息发布到`Exchange`上, 消息最终到达队列并被消费者接收, 而`Binding`决定交换器的消息应该发送到哪个队列.

![消息路由](images\消息路由.png)

#### Exchange类型

`Exchange`分发消息时更具类型的不同分发策略有区别, 目前共四种类型: `direct`, `fanout`, `topic`, `headers`.

`headers`匹配AMQP消息的`header`而不是路由键, `header`交换器和`direct`交换器完全一致, 但性能差很多, 目前几乎用不到了, 所以直接使用另外三种类型.

##### `direct Exchange`

直连式交换器, 消息中的路由键`routing key`如果和`Binding` 中的 `binding key`一致, 交换器就会将消息发送到对应的队列中, 路由键与队列名完全匹配.

![direct](images\direct.png)

##### `Fanout Exchange`

每个发到`fanout`类型交换器的消息都会分到所有绑定的队列上去. `fanout`交换器不处理路由键, 只是简单的将队列绑定到交换器上, 每个发送到交换器的消息都会被转发到与该交换器绑定的所有队列上. 类似子网广播, 每台子网内的主机都获得了一份复制的消息. `fanout`类型转发消息式最快的.

![fanout](images\fanout.png)

##### `Topic Exchange`

`topic`交换器通过模式匹配分配消息的路由键属性, 将路由键和某个模式进行匹配, 此时队列需要绑定到一个模式上. 它将路由键和绑定键的字符串切分成单词, **这些单词之间用点隔开**. 它同样也会识别两个通配符: `#` , `*` . `#`匹配零个或多个单词, `*` 匹配一个单词. 

![topic](images\topic.png)

## RabbitMQ整合

### 安装RabbitMQ

在Docker中下载RabbitMQ镜像, 镜像版本中带有[`management`]后缀的为有管理页面的版本

启动时映射端口, 默认端口`5672`, 管理页面端口`15672`

启动后可通过浏览器访问其管理页面, 默认用户名/密码: guest/guest

![管理页面](images\管理页面.png)

### 引入依赖坐标

`spring-boot-starter-amqp`

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### 配置RabbitMQ

在主配置文件中配置

```yaml
# 配置RabbitMQ
spring:
  rabbitmq:
    host: 172.20.10.2
    port: 5672
    username: guest
    password: guest
    virtual-host: /
```

### `RabbitTemplate`

`RabbitTemplate`可实现对消息的发送和接收, 以及对对象的自动序列化操作, `RabbitAutoConfigurtion`已经自动配置了`RabbitTemplate`, 只需要在容器中获取

```java
@SpringBootTest
class AmqpApplicationTests {

   @Autowired
   RabbitTemplate template;

   @Test
   void contextLoads() {
      ...
   }
}
```

使用`RabbitTemplate`的`convertAndSend()`方法可以将对象实现java的序列化并发送

```java
@Test
void contextLoads() {
   UserInfo userInfo = new UserInfo("001", "陈晓龙", "12345678");
   //对象被默认序列化后发送
   template.convertAndSend("exchange.direct", "study.news", userInfo);
}
```

在队列中查看其消息结果如图:![默认序列化](images\默认序列化.png)

使用`RabbitTemplate`的`receiveAndConvert()`方法可接收队列中的消息, 并反序列化为java对象

```java
@Test
public void receive(){
   //接收数据
   Object o = template.receiveAndConvert("study.news");
   System.out.println(o.getClass());
   System.out.println(o);
}
```

其结果为:![反序列化](images\反序列化.png)

---

#### 序列化为Json数据

编写配置类, 使用`Jackson2JsonMessageConverter`替换原有的序列化, 并注入到容器中

```java
@Configuration
public class MyAmqpConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
}
```

其在队列中的结果为:![json](images\json.png)

---

### `AmqpAdmin`

使用`AmqpAdmin`可以通过java代码来实现`Exchange`, `Binding`, `Queue` 的创建和删除

```java
@SpringBootTest
class AmqpApplicationTests {

   @Autowired
   AmqpAdmin amqpAdmin;

   /**
    * 创建交换器
    */
   @Test
   public void createExchange(){
      amqpAdmin.declareExchange(new DirectExchange("exchange.amqpAdmin"));
   }

   /**
    * 创建队列
    */
   @Test
   public void createQueue(){
      amqpAdmin.declareQueue(new Queue("queue.amqpAdmin", true));
   }

   /**
    * 绑定规则
    */
   @Test
   public void creatBinding(){
      amqpAdmin.declareBinding(new Binding("queue.amqpAdmin", Binding.DestinationType.QUEUE, "exchange.amqpAdmin", "queue.#", null));
   }

}
```

