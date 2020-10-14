# Spring Boot 与检索

> 我们的应用经常需要添加检索功能, 开源的`ElasticSearch`是目前全文搜索引擎的首选. 他可以快速存储, 搜索和分析海量数据. Spring Boot通过整合`Spring Data ElasticSearch`为我们提供了非常便捷的检索功能支持
>
> `Elasticsearch`是一个分布式搜索服务, 提供Restful API, 底层基于Lucene, 采用多shard(分片)的方式保证数据安全, 并且自动提供`resharding`功能, `Github`等大型的站点也是采用了`ElasticSearch`作为其搜索服务.

## 安装`ElasticSearch`

在Linux下启动Docker, 并下载`ElasticSearch`的官方镜像, 通过命令启动`ElasticSearch`, 因为`ElasticSearch`启动时, 运行内存需大于2G, 否则启动报错. 如果主机运行内存不满足需求, 启动时可通过添加`-e ES_JAVA_OPTS="-Xms256m -Xmx256m"`指令限制其运行内存大小.

`ElasticSearch`的默认端口为`9200`, 其节点(索引)端口为`9300`

eg: `docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9200:9200 -p 9300:9300 --name es01 elasticsearch`

启动后可在浏览器中输入主机+端口号进行访问验证, 如显示以下`json`信息则运行成功

```json
{
  "name" : "biAI9ZL",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "htyoDltpQAGUGSmQHPlDyA",
  "version" : {
    "number" : "5.6.12",
    "build_hash" : "cfe3d9f",
    "build_date" : "2018-09-10T20:12:43.732Z",
    "build_snapshot" : false,
    "lucene_version" : "6.6.1"
  },
  "tagline" : "You Know, for Search"
}
```

[官方帮助文档](https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html)

---

## 使用`ElasticSearch`

可参考官方文档: https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html

---

## 整合Spring Boot

### 引入依赖坐标

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

### 使用`ElasticSearch`

在spring boot中有两种技术可以和ES交互, 同时可编写`ElasticsearchRepository`子接口交互

#### `RestClient`

Jest默认时不生效的, 

#### `SpringData ElasticSearch`

#### `ElasticsearchRepository`子接口

