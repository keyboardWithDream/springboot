# Spring Boot与Web开发

使用SpringBoot

1. 创建SpringBoot应用, 选中我们需要的模块(场景)
2. SpringBoot已经默认将这些场景配置完成(自动配置原理), 只需要在配置文件中指定少量配置就可以运行
3. 自己编写业务代码

---

## 一. SpringBoot 静态资源的映射规则

`WebMvcAutoConfiguration`自动配置类

```java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
    } else {
        Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
        CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        if (!registry.hasMappingForPattern("/webjars/**")) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"}).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }

        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            this.customizeResourceHandlerRegistration(registry.addResourceHandler(new String[]{staticPathPattern}).addResourceLocations(WebMvcAutoConfiguration.getResourceLocations(this.resourceProperties.getStaticLocations())).setCachePeriod(this.getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }
    }
}
```

---

`ResourceProperties`资源配置类

```java
@ConfigurationProperties(
    prefix = "spring.resources",
    ignoreUnknownFields = false
)
public class ResourceProperties {
    ...
    //可以设置和金泰资源有关的参数, 缓存时间等
}
```

---

`WelcomePageHandlerMapping`欢迎页映射

```java
@Bean
public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext, FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
    WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext), applicationContext, this.getWelcomePage(), this.mvcProperties.getStaticPathPattern());
    welcomePageHandlerMapping.setInterceptors(this.getInterceptors(mvcConversionService, mvcResourceUrlProvider));
    welcomePageHandlerMapping.setCorsConfigurations(this.getCorsConfigurations());
    return welcomePageHandlerMapping;
}
```

---

1. 所有`/webjars/**`, 都去`classpath:/META-INF/resources/webjars/`下寻找资源

   `webjars` : 以`jar`包的方式引入静态资源, [官方网站](https://www.webjars.org/)

   可以使用`maven`形式导入

   ![webjars](D:\Code\springboot\web\note\images\webjars.png)

   其访问路径为: `localhost:8080/webjars/jquery/3.5.1/jquery.js`

2. `/**` : 访问当前项目任何资源(静态资源的文件夹)

   ```
   classpath:/META-INF/resources/
   classpath:/resources/
   classpath:/static/
   classpath:/public/
   / : 当前项目的根路径
   ```

3. 欢迎页: 静态资源文件下的所有`index.html`页面, 被`/**` 映射

   `localhost:8080/` 默认找 `index`页面

---

## 二. 模板引擎

模板引擎有: JSP, Velocity, Freemark, Thymeleaf 等等...

SpringBoot推荐使用`Thymeleaf `

[官方文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax)

### 1. 引入`Thymeleaf `

引入启动器

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

修改`thymeleaf`的版本

```xml
<properties>
   <java.version>11</java.version>
   <thymeleaf.version>3.0.11.RELEASE</thymeleaf.version>
   <!-- 布局功能的支持程序 -->
   <thymeleaf-layout-dialect.version>2.5.1</thymeleaf-layout-dialect.version>
</properties>
```

---

### 2. thymeleaf使用

导入thymeleaf的名称空间

```html
<html lang="ch" xmlns:th="http://www.thymeleaf.org">
```

```html
<h1 id="h101" class="myH1" th:id="msg" th:class="myMsg" th:text="${msg}">这是显示欢迎信息</h1>
```

---

### 3. 语法规则

| 序号 | 特征                   | 属性                                       |
| :--- | :--------------------- | :----------------------------------------- |
| 1    | 片段包含(`jsp:includ`) | `th:insert` `th:replace`                   |
| 2    | 片段迭代(`c:forEach`)  | `th:each`                                  |
| 3    | 条件评估(`c:if`)       | `th:if` `th:unless` `th:switch` `th:case`  |
| 4    | 局部变量定义(`c:set`)  | `th:object` `th:with`                      |
| 5    | 常规属性修改           | `th:attr` `th:attrprepend` `th:attrappend` |
| 6    | 特定属性修改           | `th:value` `th:href` `th:src` `...`        |
| 7    | 文字（标签正文修改）   | `th:text`(转义)`th:utext`(不转义)          |
| 8    | 声明片段               | `th:fragment`                              |
| 9    | 碎片清除               | `th:remove`                                |

### 4. 表达式

- 简单表达式：

  - 变量表达式： `${...}`

    ```html
    <p>Today is: <span th:text="${today}">13 february 2011</span>.</p>
    ```

  - 选择变量表达式： `*{...}` - 和`${}`在功能上是一样的

    ```html
    <p th:utext="#{home.welcome}">Welcome to our grocery store!</p>
    ```

  - 消息表达： `#{...}` - 获取国际化内容

    ```html
    <p>
      Today is: <span th:text="${#calendars.format(today,'dd MMMM yyyy')}">13 May 2011</span>
    </p>
    ```

  - 链接URL表达式： `@{...}` - 定义URL 

    ```html
    <a th:href="@{${url}(orderId=${o.id})}">view</a>
    <a th:href="@{'/details/'+${user.login}(orderId=${o.id})}">view</a>
    ```

  - 片段表达式： `~{...}`

    ```html
    <div th:insert="~{commons :: main}">...</div>
    ```

- 文字

  - 文本文字：`'one text'`，`'Another one!'`，...
  - 号码文字：`0`，`34`，`3.0`，`12.3`，...
  - 布尔文字：`true`，`false`
  - 空文字： `null`
  - 文字标记：`one`，`sometext`，`main`，...

- 文字操作：

  - 字符串串联： `+`
  - 文字替换： `|The name is ${name}|`

- 算术运算：

  - 二元运算符：`+`，`-`，`*`，`/`，`%`
  - 减号（一元运算符）： `-`

- 布尔运算：

  - 二元运算符：`and`，`or`
  - 布尔否定（一元运算符）： `!`，`not`

- 比较和平等：

  - 比较：`>`，`<`，`>=`，`<=`（`gt`，`lt`，`ge`，`le`）
  - 等号运算符：`==`，`!=`（`eq`，`ne`）

- 条件运算符：

  - 如果-则： `(if) ? (then)`
  - 如果-则-否则： `(if) ? (then) : (else)`
  - 默认： `(value) ?: (defaultvalue)`

- 特殊令牌：

  - 无操作： `_`



**实例:**

```html
<!DOCTYPE html>
<html lang="ch" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Success</title>
</head>
<body>
<h1>Success!</h1>
<!-- th:text 将<h1>中的文本内容设置为指定的值 -->
<h1 id="h101" class="myH1" th:id="msg" th:class="myMsg" th:text="${msg}">这是显示欢迎信息</h1>
<hr>
<div th:text="${msg}"></div>
<div th:utext="${msg}"></div>
<hr>
<h4 th:each="user : ${users}" th:text="${user}"></h4>
<hr>
<h4>
    <span th:each="user : ${users}">[[${user}]]</span>
</h4>
</body>
</html>
```

---

## 三. 扩展SpringMVC

编写一个配置类`@Configuration`, 是`WebMvcConfigurerAdapter`类型; 不能标注`@EnableWebMvc`

```java
/**
 * 使用WebMvcConfigurerAdapter可以扩展SpringMVC的功能
 * @Author Harlan
 * @Date 2020/10/5
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //访问/springboot, 跳转到success.jsp
        registry.addViewController("/springboot").setViewName("success");
    }
}
```

其保留了所有的自动配置, 也能用我们的扩展配置

### 全面接管SpringMVC

SpringBoot对SpringMVC的自动配置不使用, 使用用户自己的配置

只需要在配置类中添加`@EnableWebMvc`注解

---

---

## 四. 国际化

1. **编写国际化配置文件**
2. 使用`ResourceBundleMessageSource`管理国际化资源文件
3. 在页面使用`fmt:message`取出国际化内容

**步骤:**

1. 编写国际化配置文件, 抽取页面需要显示的国际化消息

   ![i18nproperties](D:\Code\springboot\web\note\images\i18nproperties.png)

   其中包括一个默认配置`login.properties`

   文件命名为规则: 页面名+语言缩写+国家简写

   其文件内容为:![i18ncontent](D:\Code\springboot\web\note\images\i18ncontent.png)

   可点击IDE下方的`ResourceBundle`按钮进行多个文件快速编辑:![bundle](D:\Code\springboot\web\note\images\bundle.png)

   编辑效果如图:![quick](D:\Code\springboot\web\note\images\quick.png)

2. SpringBoot自动配置好了管理国际化资源文件的组件

   指定基础名

   ```yaml
   spring:
     messages:
       basename: i18n.login
   ```

3. 在页面中使用`#{...}`获取国际化的值

   ```html
   <input type="text" placeholder="Username" th:placeholder="#{login.username}">
   <button type="submit">[[#{login.btn}]]</button>
   ```

   其根据浏览器语言设置的信息切换了国际化

---

### 自定义国际化

国际化实现原理是通过获取请求头中`Accept-Language: zh-CN,zh`参数![requestHeaders](D:\Code\springboot\web\note\images\requestHeaders.png)

我们可以通过实现`LocaleResolver`接口中的方法, 获取到请求体中的信息进行自定义国际化

```java
public interface LocaleResolver {
    Locale resolveLocale(HttpServletRequest var1);

    void setLocale(HttpServletRequest var1, @Nullable HttpServletResponse var2, @Nullable Locale var3);
}
```

`resolveLocale`方法返回一个`Locale`对象

`setLocale`方法可以设置`Locale`对象

#### **实现`LocaleResolver`接口**

请求路径中携带关于地区语言的参数信息

```html
<a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
<a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
```

在`HttpServletRequest`中获取请求参数

通过构造器实例化`Locale`对象, 传入参数, 并返回

```java
/**
 * 在链接上携带区域信息
 * @Author Harlan
 * @Date 2020/10/5
 */
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        Locale locale = null;
        String l = httpServletRequest.getParameter("l");
        if (!StringUtils.isEmpty(l)){
            System.out.println(l);
            //获取国际语言信息
            String[] local = l.split("_");
            locale = new Locale(local[0], local[1]);
        }else {
            //获取默认的信息
            locale = Locale.getDefault();
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
```

最后将自定义的国际化类注入到容器中(扩展Mvc类)

```java
/**
 * 使用WebMvcConfigurerAdapter可以扩展SpringMVC的功能
 * @Author Harlan
 * @Date 2020/10/5
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
```

==**注意**: 其方法名必须为 `localeResolver`==

