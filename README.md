# Token Demo

#### 项目介绍
使用`springcloud`搭建一个服务生产者，消费者调用生产者需要token认证

#### 技术依赖 ####

- `Spring Boot(Cloud)`：项目基础架构
- `MyBatis|Mybatis-plus`：用于访问`MySQL`数据库 
- `Redis`：用于存储token，和用户信息
- `MD5`：用户密码使用MD5摘要存储

#### 环境依赖 ####

- `JDK8+`
- `MySQL5.1.42+`
- `Redis单机`：jedis2.9.0

#### 项目说明 ####

- `token-parent`：父工程，聚合项目
- `token-common`：公共模块，jar包,用来存放所有bean,常量,service接口,util。需要注意的是，resources目录下有该项目的初始化sql
- `token-eureka-server`：eureka注册中心，用来注册服务，提供服务列表
- `token-server`：token服务模块，包含，token的创建，token的校验，重置有效时间，token的注销
- `token-module-01`：服务的生产者，提供了一个根据id查询用户的接口，需要token
- `token-module-02`：服务的调用者，提供了一个根据id查询用户的接口,该接口使用springcloud的ribbon+RestTemplate调用token-module-01

------

#### 注意事项 ####

- 初始化sql，只有一张表，在token-common项目的resouces下
- redis只有在token-server中使用，因此，你是用的时候需要配置redis,如，配置host,密码等。
- 要注意redis的备份文件dump.rdb所在的目录是否redis有权限写，否则，redis服务会报无权限持久化的错误。
