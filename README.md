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
- 添加参数校验、异常处理和自动化测试

## 技术版本

- Java 8
- Spring Boot 2.7.18
- Maven
- IntelliJ IDEA

Spring Boot 2.7.18 可以使用 Java 8，适合在学习阶段保持与 Java 8 项目的运行环境一致。

## 当前学习进度

| 节次 | 学习内容 | 状态 |
| --- | --- | --- |
| 第 1～4 节 | 环境检查、IDEA 导入、启动项目、认识目录 | 已完成 |
| 第 5 节 | 理解 Maven 和 `pom.xml` | 已完成 |
| 第 6 节 | 创建 Book 对象并返回 JSON | 已完成 |
| 第 7 节 | 使用 List 保存并查询多本图书 | 已完成 |
| 第 8 节 | 使用 POST 新增图书 | 已完成 |

## 从这里开始

第一次学习时，请打开 [完整学习目录](docs/README.md)，严格按照文档编号依次操作。

已经完成前面课程、准备继续当前进度时，请阅读：

- [第 8 节：使用 POST 新增图书](docs/08-使用POST新增图书.md)
- [IDEA HTTP 请求文件](requests/book-api.http)

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

新增图书的请求体示例：

```json
{
  "title": "Spring 实战",
  "author": "赵六"
}
```

POST 请求不能直接通过浏览器地址栏完成。可以使用 IDEA 打开 [requests/book-api.http](requests/book-api.http) 发送请求；如果当前 IDEA 提示需要许可证才能使用 HTTP Client，直接使用 macOS 终端和 `curl` 即可，不需要为本课程购买或试用许可证。详细操作见第 8 节文档。

## 学习文档

0. [如何重现每一节代码](docs/00-如何重现每一节代码.md)
1. [检查开发环境](docs/01-检查开发环境.md)
2. [使用 IDEA 导入项目](docs/02-使用IDEA导入项目.md)
3. [启动项目并访问接口](docs/03-启动项目并访问接口.md)
4. [认识项目结构](docs/04-认识项目结构.md)
5. [理解 Maven 和 pom.xml](docs/05-理解Maven和pom.xml.md)
6. [创建 Book 对象并返回 JSON](docs/06-创建Book对象并返回JSON.md)
7. [使用 List 查询全部图书](docs/07-使用List查询全部图书.md)
8. [使用 POST 新增图书](docs/08-使用POST新增图书.md)

## 已完成课程的代码快照

| Git 标签 | 对应内容 |
| --- | --- |
| `lesson-01-hello` | 启动项目并访问 `/hello` |
| `lesson-02-book-json` | 返回一本图书的 JSON |
| `lesson-03-book-list` | 使用 List 查询全部图书和指定图书 |
| `lesson-04-book-create` | 使用 POST 和 JSON 新增图书 |

标签的查看、切换和源码导出方法见[如何重现每一节代码](docs/00-如何重现每一节代码.md)。

## 运行自动化测试

在项目根目录执行：

```bash
mvn test
```

看到 `BUILD SUCCESS` 表示自动化测试通过。自动化测试通过后，仍然需要按照对应课程文档进行一次手动接口验证。

## 每一节的完成规则

每一节都按照下面的顺序完成：

1. 编写本节代码。
2. 补充详细学习文档和根目录 README。
3. 运行自动化测试。
4. 按文档在 IDEA 或浏览器中手动验证。
5. 保存验证截图（适用时）。
6. 提交代码并创建课程 Git 标签。
7. 推送提交和标签到远程仓库。

只有代码和文档经过验证后，才会标记这一节为已完成。
