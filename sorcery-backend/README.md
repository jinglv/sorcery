# Sorcery接口工具平台

## Git使用

```shell
echo "# sorcery" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/jinglv/sorcery.git
git push -u origin main
```

## 业务（功能）架构

- 顶层：用户服务，如注册登录、权限等
- 中间层：实际业务
- 底层：管理后台，如文件上传、数据统计系统推送等

## 技术架构

- 技术选型：SpringBoot2.x + MySQL + MyBatis + Maven（开发环境JDK1.8）
- 开发模式：项目采用经典MVC，控制层（Controller）、服务层（Service层）、数据层（DAO层）

## 部署架构

- 前端：服务转发 + 负载均衡
- 后端：业务处理 + 功能实现
- 工具：缓存、队列

## 热部署

- 热部署就是当应用程序正在运行的时候升级软件或者修改某一部分代码、配置文件时，无需手动重启应用，即可使修改的部分生效
- 热部署方式：spring-boot-devtools工具 + IDEA配置

步骤：

1. 开启IDEA自动构建选项(Build project automatically)选项，在Registry中勾选Compile autoMake allow when app running
2. 启动项中开启热部署(Running Application Update Policies)
3. pom文件中添加spring-boot-devtools依赖，配置文件中添加配置

## RESTFul风格接口设计

- RESTFul架构、HTTP方法语义、HTTP方法幂等性、RESTFul接口设计原则

### RESTFul介绍

- REST全称Representational State Transfer，中文为表述性状态转移，REST指的是一组架构约束条件和原则
- RESTFul表述的是资源的状态性转移，在Web中资源就是URI（Uniform Resource Identifier）
- 如果一个架构符合REST的约束条件和原则，我们就称它为RESTFul架构，HTTP是目前与REST相关的唯一实例
- RESTFul架构应该遵守统一的接口原则，应该使用标准的HTTP方法如GET和POST，并且遵循这些方法的语义

### HTTP方法的语义

| 方法   | 语义                                                         |
| ------ | ------------------------------------------------------------ |
| GET    | 获取指定的资源                                               |
| DELETE | 删除指定的资源                                               |
| POST   | 发送数据给服务器，依据HTTP1.1规范中的描述，结合实际项目开发经验，POST经常为了以统一的方法来涵盖一下功能：<br />1.在公告板、新闻组、邮件列表或类似的文章组发布消息<br />2.通过注册新增用户<br />3.向数据处理程序提供一批数据，例如提交一个表单 |
| PUT    | 使用请求中的负责创建或者替换目标资源。PUT和POST的区别在于PUT是幂等的，而POST不是。<br />幂等的含义可以理解为调用一次与连续调用多次是等价的（没有副作用或副作用不变） |

### POST和PUT区别

- 比较容易混淆的是HTTP POST和PUT
- POST和PUT的区别容易被简单的误认为i"POST表示创建资源，PUT表示更新资源"
- **实际上，二者均可用于创建资源，更为本质的差别是在幂等性方面**

### HTTP的幂等性

| 方法   | 幂等性       | 幂等性分析                                                   |
| ------ | ------------ | ------------------------------------------------------------ |
| GET    | 具备幂等性   | 用于获取资源，没有副作用，所以是幂等的。请注意，这里强调的是一次和N次并不是说每次请求的结果相同，而是每次请求不会产生不同的副作用。 |
| DELETE | 具备幂等性   | 用于删除资源，有副作用，但它应该满足幂等性，调用一次和N次对系统产生的副作用是相同。 |
| POST   | 不具备幂等性 | POST所指向资源并非POST要创建的资源本身，而是POST创建资源的接受者，比如POST:/news的含义是在news新闻组这个资源分类下新建一条新的新闻，所以两次相同的POST请求会在服务器端创建两份新的资源，它们是不同的。所以，POST方法不具备幂等性。 |
| PUT    | 具备幂等性   | PUT对应的资源是要创建或更新的资源本身，语义是创建或更新，对同一资源进行多次PUT的副作用和一次PUT是相同的，因此PUT方法具有幂等性。 |

副作用说明：

1. 例如put请求修改用户名，请求一次或多次，结果是用户名一直在改变，产生的副作用就是当前用户信息改变了，说明副作用的结果是相同
2. 例如get查询，只是查询一个资源，每次查询影响是一样的，既不会修改，也不会删除，本身是没有任何其他副作用的

### RESTFul接口URL命名原则

- 命名原则1：HTTP方法后跟的URL必须是名词且统一成名词复数形式
- 命名原则2：URL中不采用大小写混合的驼峰命名，尽量采用全小写单词，如果需要连接多个单词，则采用"-"连接
- 示例：/users /users-fans 反例：/getUsers /getUsersFans

## RESTFul接口URL分级原则

- 分级原则1：一级用来定位资源分类，如/users即表示需要定位到用户相关资源
- 分级原则2：二级仍用来定位具体某个资源，如/users/20即表示id为20的用户，再例如/users/20/fans/1即表示id为20的用户的id为1的粉丝
- 建议：原则是为了让我们的开发更加规范，但是不能成为束缚我们开发的加锁

## RESTFul接口命名示例

- GET、POST、PUT、DELETE接口命名示例

| URI       | 方法   | 功能             |
| --------- | ------ | ---------------- |
| /users    | GET    | 获取用户列表     |
| /users/20 | GET    | 获取id为20的用户 |
| /users    | POST   | 创建用户         |
| /users/20 | PUT    | 修改id为20的用户 |
| /users    | PUT    | 批量修改用户     |
| /users/20 | DELETE | 删除id为20的用户 |

- 复杂GET查询请求接口命名示例

| URI                                                      | 方法 | 功能           |
| -------------------------------------------------------- | ---- | -------------- |
| /users?gender=male                                       | GET  | 过滤           |
| /users?sort=created-time-desc                            | GET  | 排序           |
| /users?gender=male&sort=created-time-desc                | GET  | 过滤+排序      |
| /users?name=hellostar&gender=male&sort=created-time-desc | GET  | 搜索+过滤+排序 |
| /users?size=10&no=1                                      | GET  | 分页           |

## 通用功能与配置

- 通用功能：加解密工具（AES、RSA、MD5）-工具包commons-codec、Json数据返回类
- 通用配置：Json信息转换配置、全局异常处理配置

## 模块开发

### 用户模块开发

#### 用户注册与登录

- 数据库表设计：用户表、用户信息表
- 相关接口（API）：获取RSA公钥、用户注册、用户登录

## JWT

### 基于JWT的用户token验证

- 基于session的身份验证
- 验证过程：服务端验证浏览器携带的用户名和密码，验证通过后生成用户凭证保存在服务端（session），浏览器再次访问时，服务端查询session，实现登录状态保持
- 缺点：随着用户的增多，服务端压力增大；若浏览器cookie被攻击者拦截，容易受到跨站请求伪造攻击；分布式系统下扩展性不强

- 基于token的用户身份验证
- 验证过程：服务端验证浏览器携带的用户名和密码，验证通过后生成用户令牌（token）并返回给浏览器，浏览器再次访问时携带token，服务端校验token并返回相关数据
- 优点：token不存储在服务器，不会造成服务器压力；token可以存储在非cookie中，安全性搞；分布式系统下扩展性强

- JWT：全程JSON Web Token，JWT是一个规范，用于在空间受限环境下安全传递"声明"
- JWT的组成：分成三个部分
    - 第一部分是头部（header），声明的类型、声明的加密算法（通常使用SHA256）
    - 第二部分是载荷（payload），存放有效信息，一般包含签发者、所面向的用户、接受方、过期时间、签发时间以及唯一身份标识
    - 第三部分是签名（signature），主要由头部、载荷以及密钥组合加密而成
- JWT优点：跨语言支持、便于传输、易于扩展

## common-random

测试数据生成工具类：https://github.com/yindz/common-random