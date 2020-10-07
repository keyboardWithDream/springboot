# Docker入门

> Docker 是一个[开源](https://baike.baidu.com/item/开源/246339)的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 [Linux](https://baike.baidu.com/item/Linux)或[Windows](https://baike.baidu.com/item/Windows/165458) 机器上，也可以实现[虚拟化](https://baike.baidu.com/item/虚拟化/547949)。容器是完全使用[沙箱](https://baike.baidu.com/item/沙箱/393318)机制，相互之间不会有任何接口。一个完整的Docker有以下几个部分组成：`DockerClient`客户端, `Docker Daemon`守护进程, `Docker Image`镜像, `DockerContainer`容器.

## Docker核心概念

![docker-core](C:\Users\13536\Desktop\docker-core.png)

Docker主机(`Host`): 安装了Docker程序的机器(==Docker运行在操作系统之上==)

Docker客户端(`Client`): 连接到Docker主机进行操作的客户端

Docker仓库(`Registry`): 用来保存各种打包好的软件镜像

Docker镜像(`Images`): 软件打包好的镜像

Docker容器(`Container`): 镜像启动后的实称为一个容器

---

---

## 安装Docker(Linux)

Docker 要求 CentOS 系统的内核版本高于3.10 : `uname -r`![系统版本](C:\Users\13536\Desktop\系统版本.png)

升级软件包及内核 : `yum update` ![升级内核](C:\Users\13536\Desktop\升级内核.png)

安装Docker : `yum install docker`

启动Docker: `systemctl start docker`

查看Docker版本号: `docker -v`

设置开机启动Docker: `systemclt enable docker`

停止Docker: `systemctl stop docker`

---

## 使用Docker的步骤

1. 安装Docker
2. 去Docker仓库找到软件对应的镜像
3. 使用Docker运行镜像, 生成一个Docker容器
4. 对容器的启动停止就是对软件的启动和停止

---

## 常用操作

### 镜像操作

| 操作 | 命令                                            | 说明                                                         |
| ---- | ----------------------------------------------- | ------------------------------------------------------------ |
| 检索 | `docker search 关键字` eg:`docker search redis` | 在docker hub中检索镜像的详细信息                             |
| 拉取 | `docker pull 镜像名[:tag]`                      | `:tag`是可选的, `tag`表示标签, 多为软件的版本, 默认是`latest` |
| 列表 | `docker images`                                 | 查看所有本地的镜像                                           |
| 删除 | `docker rmi images-id`                          | 删除指定的本地镜像                                           |

### 容器操作

| 操作     | 命令                                                         | 说明                                                         |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 运行     | `docker run --name container-name -d image`, eg:`docker run --name myredis -d redis` | `--name`: 定义容器名,  `-d`: 后台运行, `image-name`: 指定镜像模板 |
| 列表     | `docker ps`                                                  | 查看运行的容器, `-a`可查看的容器                             |
| 停止     | `docker stop container-name / container-id`                  | 停止指定的容器                                               |
| 删除     | `docker rm container-id`                                     | 删除指定容器                                                 |
| 启动     | `docker start container-name / container-id`                 | 启动指定的容器                                               |
| 端口映射 | `-p xxxx:xxxx`, eg:`docker run -d -p 6379:6370 --name myredis docker.io/redis` | `-p`: 主机端口(映射到)容器内部的端口                         |
| 容器日志 | `docker logs container-name / container-id`                  |                                                              |
| 更多命令 | https://docs.docker.com/docker-hub/                          |                                                              |



