# SpringBoot与缓存

## JSR-107

> Java Caching定义了5个核心接口, 分别是`CachingProvider`, `CacheManager`, `Cache`, `Entry`, `Expiry`
>
> 由于操作繁琐不推荐使用(不讲解)

### `CachingProvider`

>缓存提供者: 定义了创建, 配置, 获取, 管理和多个`CacheManager`. 一个应用可以在运行期间访问多个`CachingProvider`.

### `CacheManager`

> 缓存管理器: 定义了创建, 配置, 获取, 管理和多个唯一命名的`Cache`, 这些`Cache`存在于`CacheManager`的上下文中, 一个`CacheManager`仅被一个`CachingProvider`所拥有.

### `Cache`

> 缓存: 一个类似`Map`的数据结构并临时存储以`key`为索引的值, 一个`	Cache`仅被一个`CacheManager`所拥有.

### `Entry`

> 数据: 一个存贮在`Cache`中的`key-value`(键值)对.

### `Expiry`

> 有效期: 每一个存储在`Cache`中的条目有一个定义的有效期, 一旦超过了这个时间, 条目为过期的状态. 一旦过期, 条目将不可访问, 更新, 和删除. 缓存有效期可以通过`ExpiryPolicy`设置.

---

## Spring缓存抽象

> Spring从3.1开始定义了`org.springframework.cache.Cache`和`org.springframework.cache.CacheManager`接口来统一不同的缓存技术, 并支持使用`JCache(JSR-107)`注解开发
>
> `Cache`接口为缓存的组件规范定义, 包含缓存的各种操作集合;
>
> `Cache`接口下Spring提供了各种`xxxCache`的实现, 如`RedisCache`, `EhCacheCahe`, `ConcurrentMapCache`等
>
> 每次调用需要缓存功能的方法时, Spring会检查指定参数的指定的目标方法是否已经被调用过; 如果有就直接从缓存中获取方法调用后的结果, 如果没有就调用方法并缓存结果返回给用户, 下次调用直接从缓存中获取.
>
> 使用Spring缓存抽象时需要关注两点:
>
> 1. 确定方法需要被缓存以及他们的缓存策略
> 2. 从缓存中读取之前缓存存储的数据

### 缓存注解

| 注解             | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| `@Cacheable`     | 注意针对方法配置, 能够根据方法的请求参数对其结果进行缓存     |
| `@CacheEvict`    | 清空缓存                                                     |
| `@CachePut`      | 保证方法被调用, 又希望结果被缓存(更新缓存)                   |
| `@EnableCaching` | 开启基于注解的缓存                                           |
| **概念**         | **说明**                                                     |
| Cache            | 缓存接口, 定义缓存操作. 实现有 `RedisCache`, `EhCacheCahe`... |
| CacheManager     | 缓存管理器, 管理各种缓存(`Cache`)组件                        |
| KeyGenerator     | 缓存数据时`key`生成策略                                      |
| serialize        | 缓存数据时`value`序列化策略                                  |

### 使用

#### 选择的场景启动器

`cache`场景

#### 开启注解

在启动类中加入`@EnableCaching`注解

```java
@SpringBootApplication
@MapperScan(basePackages = {"com.study.cache.dao"})
@EnableCaching
public class CacheApplication {

   public static void main(String[] args) {
      SpringApplication.run(CacheApplication.class, args);
   }

}
```

#### 添加注解

在`Service`中的方法上添加`@Cacheable`注解, 将返回值添加到缓存中

`@Cacheable`属性:

1. `cacheNames`/`value` : 指定缓存组件的名字
2. `key` : 缓存数据使用的key, 默认使用方法参数的值, 可使用`SpEL`表达式
3. `keyGenerator` : key的生成器, 可以指定key的生成器的组件id (`key` 和`keyGenerator` 二选一使用)
4. `cacheManager` : 指定缓存管理器, 或指定`cacheResolver`缓存解析器
5. `condition` : 指定符合条件的情况下才缓存, 使用`SpEL`表达式
6. `unless` : 否定缓存, 当条件为`true`, 则不会被缓存(与`condition`相反), 可以获取到结果进行判断
7. `sync` : 是否使用异步模式

```java
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Cacheable(cacheNames = {"emp"}, key = "#id", condition = "#id>0", unless = "#result==null")
    @Override
    public Employee getEmp(Integer id) {
        return employeeDao.getEmpById(id);
    }
}
```