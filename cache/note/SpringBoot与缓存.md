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

### 使用缓存

#### 选择的场景启动器

`cache`场景, 在没有引入其它`provider`时, SpringBoot默认使用`ConcurrentMapCache` (CacheManager)

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

#### 注解使用

##### `@Cacheable`

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

    /**
    * @Cacheable
    */
    @Cacheable(cacheNames = {"emp"}, key = "#root.method.name+'['+#id+']'", condition = "#root.method eq 'getEmp' and #id=1", unless = "#result==null")
    @Override
    public Employee getEmp(Integer id) {
        return employeeDao.getEmpById(id);
    }
}
```

---

##### `@CachePut`

在方法上添加`@CachePut`注解, ==先调用目标方法(不在缓存组件中查询)==, 然后缓存其目标方法的结果

在`Key`中指定相同的值, 可达到同步更新缓存的作用

```java
/**
* @CachePut
*/
@CachePut(cacheNames = {"emp"}, key = "#result.id")
@Override
public Employee updateEmp(Employee employee) {
    employeeDao.updateEmp(employee);
    return employee;
}
```

---

##### `@CacheEvict`

在方法上添加`@CacheEvict`注解, 可清除缓存

可以通过`key`指定需要清除的数据, 默认为参数值

`allEntries`属性指定为`true`时, 表示清空当前缓存组件的所有数据

`beforInvocation`属性指定为`ture`时, 表示在方法执行之前清除, 默认为`fales`(方法执行异常, 缓存也会被清除)

```java
/**
* @CacheEvict
*/
@CacheEvict(cacheNames = {"emp"}, key = "#id")
@Override
public void deleteEmp(Integer id) {
    employeeDao.deleteEmpById(id);
}
```

---

##### `@Caching`

可通过`@Caching`注解配置复杂的存贮(多个`@Cacheable` , `@CachePut`, `@CacheEvict`)将多个注解相组合.

```java
/**
* @Caching
*/
@Caching(
        cacheable = {
               @Cacheable(cacheNames = "emp", key = "#lastName")
        },
        put = {
                @CachePut(cacheNames = "emp", key = "#result.id"),
                @CachePut(cacheNames = "emp", key = "#result.email")
        }
)
@Override
public Employee getEmpByLastName(String lastName) {
    return employeeDao.getEmpByLastName(lastName);
}
```

---

##### `@CacheConfig`

通过`@CacheConfig`注解可以抽取本类中公共的`Cache`属性, 如 指定组件, `key`生成策略 等...

```java
/**
* @CacheConfig
*/
@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeServiceImpl implements EmployeeService {
	...
}
```

---

#### 添加`keyGenerator`

创建配置类, 将返回`keyGenerator`接口的实现类(此处匿名实现), 将其命名.

需要重写`generate()`方法, 参数为,目标对象 ,方法 , 参数列表. --- 方法返回值为生成`key`的字符串.

```java
@Configuration
public class MyCacheConfig {

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return method.getName()+"["+ Arrays.asList(objects).toString()+']';
            }
        };
    }
}
```

在`@Cacheable`注解中使用`keyGenerator`属性指定自己添加的策略

```java
@Cacheable(cacheNames = {"emp"}, keyGenerator = "myKeyGenerator")
@Override
public Employee getEmp(Integer id) {
    return employeeDao.getEmpById(id);
}
```

### 运行流程

`@Cacheable`标注的方法执行前, 检查缓存中是否有对应的数据

1. 方法运行之前, 先去查询`Cache`(缓存组件), 按照`cacheNames`指定的名字获取 ->`CacheManager`先获取相应的缓存, 如果是第一次获取缓存(没有`Cache`组件),`CacheManager`会将缓存组件自动创建

2. 使用`key`在`Cache`组件中查询缓存的内容, 默认为参数名(`key`是按照策略生成的: 默认使用`keyGenerator`的实现类`SimpleKeyGenerator`生成)

3. 如果没有查到对应`key`值的缓存数据, 就调用目标方法, 并将目标方法返回的结果放入缓存.

   如果查到对应`key`值的缓存数据, 就直接返回结果, 就不再调用目标方法

#### 流程核心

1. 使用`CacheManager`按照名字得到`Cache`组件
2. `key`使用`keyGenerator`生成, 默认是`SimleKeyGenerator`

---

### Cache SpEL available metadata

`root`为当前目标方法

| Name         | Location             | Describe                                         | Examples                                |
| ------------ | -------------------- | ------------------------------------------------ | --------------------------------------- |
| methodName   | root object          | 当前被调用的方法名                               | `#root.methodName`                      |
| mehtod       | root object          | 当前被调用的方法                                 | `#root.method.name`                     |
| target       | root object          | 当前被调用的目标对象                             | `#root.target`                          |
| targetClass  | root object          | 当前被调用的目标对象类                           | `#root.targetClass`                     |
| args         | root object          | 当前被调用的方法的参数列表                       | `#root.args[0]`                         |
| caches       | root object          | 当前方法调用使用的缓存列表, 即指定的`cacheNames` | `#root.caches[0].name`                  |
| argumentName | evaluation   context | 方法参数的名字                                   | `#参数名` 或 `#p0`, `#a0` - 0为参数索引 |
| result       | evaluation context   | 方法执行后的返回值                               | `#result`                               |

---

