# 从零学习 Spring Boot：图书管理 API

这是一个面向 Java 和 Spring Boot 初学者的培训项目。项目从 IDEA 导入、Maven 配置和第一个接口开始，逐步实现一个完整的图书管理 REST API。

学习过程中，`src` 目录始终保存当前最新代码；每一节验证完成后，通过 Git 提交和标签保存当时的完整代码，方便以后重新学习和重现。

## 项目目标

通过这个项目逐步掌握：

- 使用 IDEA 导入、启动和调试 Spring Boot 项目
- 理解 Maven、`pom.xml` 和项目目录结构
- 理解 Controller、HTTP 请求和 JSON
- 实现图书的新增、查询、修改和删除
- 将代码拆分为 Controller、Service、Repository
- 使用数据库保存数据
- 添加参数校验和统一异常处理
- 为三层代码补上自动化测试
- 把接口的请求和响应模型与数据库实体分开
- 用统一响应结构和业务错误码约定接口契约

## 技术版本

- Java 8
- Spring Boot 2.7.18
- Maven
- IntelliJ IDEA
- Spring Data JPA
- H2 Database
- Bean Validation（Hibernate Validator）
- JUnit 5、Mockito、AssertJ、MockMvc
- Rspress（本地文档站，仅用于阅读教程）

Spring Boot 2.7.18 可以使用 Java 8，适合在学习阶段保持与 Java 8 项目的运行环境一致。

## 当前学习进度

| 节次 | 学习内容 | 状态 |
| --- | --- | --- |
| 第 1～4 节 | 环境检查、IDEA 导入、启动项目、认识目录 | 已完成 |
| 第 5 节 | 理解 Maven 和 `pom.xml` | 已完成 |
| 第 6 节 | 创建 Book 对象并返回 JSON | 已完成 |
| 第 7 节 | 使用 List 保存并查询多本图书 | 已完成 |
| 第 8 节 | 使用 POST 新增图书 | 已完成 |
| 第 9 节 | 使用 PUT 修改图书 | 已完成 |
| 第 10 节 | 使用 DELETE 删除图书 | 已完成 |
| 第 11 节 | Controller、Service、Repository 分层 | 已完成 |
| 第 12 节 | 使用 H2 数据库和 Spring Data JPA | 已完成 |
| 第 13 节 | 参数校验和统一异常处理 | 已完成 |
| 第 14 节 | 为接口补上自动化测试 | 已完成 |
| 第 15 节 | 请求和响应模型与实体分离 | 已完成 |
| 第 16 节 | 统一响应结构和业务错误码 | 已完成 |

## 从这里开始

第一次学习时，请打开 [完整学习目录](docs/README.md)，严格按照文档编号依次操作。

已经完成前面课程、准备继续当前进度时，请阅读：

- [第 16 节：统一响应结构和业务错误码](docs/16-统一响应结构和业务错误码.md)
- [使用 curl 验证接口](docs/使用curl验证接口.md)
- [HTTP 请求示例文件](requests/book-api.http)（仅作为请求内容参考）

## 在本地以网页方式阅读文档

除了直接看 `docs/` 下的 Markdown，也可以启动本地文档站，获得侧边栏、全文搜索和上一篇下一篇导航。需要 Node.js 18 以上。

```bash
npm install
npm run dev
```

然后访问 `http://localhost:3000`。修改 Markdown 保存后页面会自动刷新。停止时按 `Control + C`。

文档站只用于本地阅读，不需要部署。`node_modules/` 和构建产物 `doc_build/` 都不会提交到仓库。

每一节文档都会说明：

- 要创建或修改哪个文件
- 在 IDEA 中如何操作
- 每段代码和重要语句的作用
- HTTP 请求是怎样进入并运行到 Java 代码的
- 应该看到什么结果
- 如何判断操作成功
- 常见错误如何排查

## 在本地运行项目

### 使用 IDEA 启动

1. 使用 IDEA 打开本项目根目录。
2. 等待 Maven 下载并加载依赖。
3. 打开 `BookApiApplication.java`。
4. 点击 `main` 方法左侧的绿色三角形。
5. 选择 **Run 'BookApiApplication'**。
6. 控制台出现 `Started BookApiApplication` 后，表示启动成功。

### 使用命令行启动

在项目根目录执行：

```bash
mvn spring-boot:run
```

默认访问地址是 `http://localhost:8080`。运行期间不要关闭 IDEA 的运行窗口或终端。

## 当前接口

| 请求方法 | 地址 | 作用 |
| --- | --- | --- |
| GET | `http://localhost:8080/hello` | 返回第一段 Spring Boot 文本 |
| GET | `http://localhost:8080/api/books` | 查询全部图书 |
| GET | `http://localhost:8080/api/books/1` | 根据编号查询图书 |
| POST | `http://localhost:8080/api/books` | 新增一本图书（第 8 节） |
| PUT | `http://localhost:8080/api/books/1` | 修改指定编号的图书（第 9 节） |
| DELETE | `http://localhost:8080/api/books/2` | 删除指定编号的图书（第 10 节） |

新增图书的请求体示例：

```json
{
  "title": "Spring 实战",
  "author": "赵六"
}
```

请求体只接受 `title` 和 `author` 两个字段。编号由数据库生成，即使在请求体里写上 `id` 也会被忽略，原因见第 15 节。

### 统一响应结构

从第 16 节开始，所有 `/api/books` 接口的响应都是同一个结构，HTTP 状态码固定 200，业务结果由 `code` 表达：

| 情况 | `code` | 响应体示例 |
| --- | --- | --- |
| 成功 | 0 | `{"code":0,"msg":"成功","data":{"id":1,"title":"Spring Boot 入门","author":"张三"}}` |
| 书名或作者为空 | 1001 | `{"code":1001,"msg":"请求参数不正确","data":{"title":"书名不能为空"}}` |
| JSON 语法错误或编号不是数字 | 1001 | `{"code":1001,"msg":"请求参数不正确","data":null}` |
| 编号不存在 | 1002 | `{"code":1002,"msg":"图书不存在，编号 99","data":null}` |
| 未预期的服务器错误 | 9999 | `{"code":9999,"msg":"服务器内部错误","data":null}` |

调用方的判断顺序是：先确认请求在网络层成功，再看 `code` 是否为 0，成功读 `data`，失败显示 `msg`。

契约变化记录：

- 第 10 节到第 15 节，删除不存在的编号返回 204 或 404；第 16 节起返回 200 加 `code` 1002。
- 第 8 节到第 15 节，新增成功返回 201；第 16 节起返回 200 加 `code` 0。
- 第 13 节到第 15 节，参数错误返回 400、编号不存在返回 404；第 16 节起统一为 200 加对应 `code`。

`/hello` 不在统一包装范围内，仍然返回纯文本。

POST、PUT 和后续的 DELETE 请求不能只靠浏览器地址栏完成。当前学习环境统一使用 macOS 自带的 `curl` 发送接口请求，不依赖 IDEA HTTP Client，也不需要为此购买或试用 IDEA 许可证。完整说明和 GET、POST、PUT 示例见[使用 curl 验证接口](docs/使用curl验证接口.md)。

修改图书的请求体示例：

```json
{
  "title": "Spring Boot 进阶",
  "author": "张三"
}
```

PUT 请求的具体操作和代码运行过程见第 9 节文档。

DELETE 请求只需要在地址中提供要删除的图书编号，不需要 JSON 请求体。具体操作和代码运行过程见第 10 节文档。

## 当前代码分层

```text
HTTP 请求
    ↓
Jackson 把 JSON 转成 BookRequest
    ↓
Bean Validation 检查 BookRequest 上的校验注解
    ↓
BookController：接收请求和返回响应
    ↓
BookMapper：BookRequest 转成 Book 实体
    ↓
BookService：组织业务步骤，找不到数据时抛出异常
    ↓
BookRepository：Spring Data JPA 数据访问接口
    ↓
Hibernate：把对象操作转换成 SQL
    ↓
H2 文件数据库：data/bookdb.mv.db
    ↓
BookMapper：Book 实体转成 BookResponse
    ↓
ApiResponseBodyAdvice：包装成 ApiResponse，补上 code 和 msg
    ↓
Jackson 生成响应 JSON
```

出错时的路径由 `GlobalExceptionHandler` 接管，把异常统一转换成 `ApiResponse`，业务返回码来自 `ErrorCode` 枚举。

图书数据已经从 ArrayList 移到 H2 文件数据库，应用停止和重新启动后数据仍然存在。`data/` 是本机运行数据目录，已加入 `.gitignore`，不会提交到仓库。

接口的输入输出由 `dto` 包下的模型类描述，实体 `Book` 只负责数据库映射，不再直接出现在接口上。

## 学习文档

0. [如何重现每一节代码](docs/00-如何重现每一节代码.md)
   - [使用 curl 验证接口](docs/使用curl验证接口.md)
1. [检查开发环境](docs/01-检查开发环境.md)
2. [使用 IDEA 导入项目](docs/02-使用IDEA导入项目.md)
3. [启动项目并访问接口](docs/03-启动项目并访问接口.md)
4. [认识项目结构](docs/04-认识项目结构.md)
5. [理解 Maven 和 pom.xml](docs/05-理解Maven和pom.xml.md)
6. [创建 Book 对象并返回 JSON](docs/06-创建Book对象并返回JSON.md)
7. [使用 List 查询全部图书](docs/07-使用List查询全部图书.md)
8. [使用 POST 新增图书](docs/08-使用POST新增图书.md)
9. [使用 PUT 修改图书](docs/09-使用PUT修改图书.md)
10. [使用 DELETE 删除图书](docs/10-使用DELETE删除图书.md)
11. [Controller、Service、Repository 分层](docs/11-Controller-Service-Repository分层.md)
12. [使用 H2 数据库和 Spring Data JPA](docs/12-使用H2数据库和SpringDataJPA.md)
13. [参数校验和统一异常处理](docs/13-参数校验和统一异常处理.md)
14. [为接口补上自动化测试](docs/14-为接口补上自动化测试.md)
15. [请求和响应模型与实体分离](docs/15-请求和响应模型与实体分离.md)
16. [统一响应结构和业务错误码](docs/16-统一响应结构和业务错误码.md)

## 已完成课程的代码快照

| Git 标签 | 对应内容 |
| --- | --- |
| `lesson-01-hello` | 启动项目并访问 `/hello` |
| `lesson-02-book-json` | 返回一本图书的 JSON |
| `lesson-03-book-list` | 使用 List 查询全部图书和指定图书 |
| `lesson-04-book-create` | 使用 POST 和 JSON 新增图书 |
| `lesson-05-book-update` | 使用 PUT 修改指定编号的图书 |
| `lesson-06-book-delete` | 使用 DELETE 删除指定编号的图书 |
| `lesson-07-layered-architecture` | 将图书功能拆分为 Controller、Service、Repository |
| `lesson-08-h2-jpa` | 使用 H2 和 Spring Data JPA 持久化图书数据 |
| `lesson-09-validation-exception` | 参数校验和统一异常处理 |
| `lesson-10-tests` | Service、Repository、Controller 三层自动化测试 |
| `lesson-11-dto` | 请求和响应模型与实体分离 |
| `lesson-12-unified-response` | 统一响应结构和业务错误码 |

标签只在代码发生变化的节建立，所以编号和文档编号不一致，例如 `lesson-11-dto` 对应第 15 节。对应关系和源码导出方法见[如何重现每一节代码](docs/00-如何重现每一节代码.md)。

## 运行自动化测试

在项目根目录执行：

```bash
mvn test
```

当前共有 27 个用例，分布在五个测试类里：

| 测试类 | 范围 | 用例数 |
| --- | --- | --- |
| `BookServiceTest` | 业务判断，使用替身，不启动 Spring | 5 |
| `BookRepositoryTest` | 实体映射和数据库操作，只启动 JPA 层 | 4 |
| `BookControllerTest` | 地址、返回码、JSON 契约，只启动 Web 层 | 9 |
| `BookMapperTest` | 三种模型之间的字段转换 | 4 |
| `ApiResponseTest` | 统一响应结构的返回码和文案 | 4 |
| `BookApiApplicationTests` | 整个应用能否启动 | 1 |

看到 `Tests run: 27, Failures: 0, Errors: 0` 和 `BUILD SUCCESS` 表示自动化测试通过。

测试使用独立的内存数据库，不会影响 `data/bookdb` 里手动练习的数据，也不需要先启动应用。自动化测试通过后，仍然需要按照对应课程文档进行一次手动接口验证。

## 每一节的完成规则

每一节都按照下面的顺序完成：

1. 编写本节代码。
2. 补充本节学习文档。
3. 更新根目录 README、`docs/README.md`、`docs/00-如何重现每一节代码.md` 和文档站侧边栏配置。
4. 运行自动化测试。
4. 按文档在 IDEA 或浏览器中手动验证。
5. 保存验证截图（适用时）。
6. 提交代码并创建课程 Git 标签。
7. 推送提交和标签到远程仓库。

只有代码和文档经过验证后，才会标记这一节为已完成。
