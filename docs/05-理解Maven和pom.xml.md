# 第 5 步：理解 Maven 和 pom.xml

## 这一节要完成什么

认识 Maven 的作用，并理解当前 `pom.xml` 中每一部分的用途。本节不修改代码。

## 1. Maven 是什么

一个普通 Java 程序只依赖 JDK；Spring Boot 项目还需要 Spring、Tomcat、Jackson、JUnit 等第三方程序包。

这些第三方程序包称为“依赖”。Maven 主要帮助完成三件事：

1. 根据 `pom.xml` 下载依赖。
2. 编译和测试项目。
3. 将项目打包成可以运行的 jar 文件。

Maven 下载的依赖默认保存在用户目录下的 `.m2/repository` 中。不同项目可以共用已经下载的依赖，不需要将 jar 文件复制进源码目录。

## 2. 打开 pom.xml

在 IDEA 左侧 Project 面板中，双击项目根目录下的 `pom.xml`。

文件开头是 Maven POM 的固定声明：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
```

现阶段不需要修改这部分。`modelVersion` 表示使用 Maven POM 4.0.0 模型。

## 3. Spring Boot 父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.18</version>
    <relativePath/>
</parent>
```

父项目提供了大量默认配置，例如：

- 常用依赖的兼容版本
- Java 编译设置
- 测试插件设置
- 资源文件编码

这里将 Spring Boot 版本固定为 `2.7.18`。

## 4. 项目坐标

```xml
<groupId>com.example</groupId>
<artifactId>book-api</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

三个值共同标识一个 Maven 项目：

- `groupId`：组织或项目组，一般使用反写域名。
- `artifactId`：项目名称。
- `version`：项目版本；`SNAPSHOT` 表示仍在开发。

打包后默认会得到类似文件：

```text
target/book-api-0.0.1-SNAPSHOT.jar
```

## 5. Java 版本

```xml
<properties>
    <java.version>8</java.version>
</properties>
```

这表示项目源码按照 Java 8 编译。代码中不能使用 Java 9 及以后才出现的语法，例如 Java `record`。

## 6. Web 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

`spring-boot-starter-web` 是 Web 开发的依赖集合，主要带来：

- Spring MVC：接收和处理 HTTP 请求。
- 内嵌 Tomcat：直接运行 Web 服务，不需要单独安装 Tomcat。
- Jackson：在 Java 对象和 JSON 之间自动转换。

`pom.xml` 没有为它填写版本，因为 Spring Boot 父项目已经管理了兼容版本。

## 7. 测试依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

它提供 JUnit、Spring Test、Mockito 等测试工具。

`scope` 为 `test` 表示这个依赖只在编译和运行测试时使用，不会作为正式业务依赖使用。

## 8. Spring Boot Maven 插件

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

这个插件可以：

- 使用 `mvn spring-boot:run` 启动项目。
- 使用 `mvn package` 构建可执行 jar。
- 将依赖一起打包，使 jar 可以通过 `java -jar` 运行。

## 9. 在 IDEA 中重新加载 Maven

以后只要修改了 `pom.xml`，都要让 IDEA 重新读取配置：

1. 打开右侧 Maven 工具窗口。
2. 点击左上角 **Reload All Maven Projects**（循环箭头）。
3. 等待依赖下载和索引结束。

## 10. 常用 Maven 命令

在项目根目录执行：

```bash
mvn clean
```

删除 `target` 构建目录。

```bash
mvn test
```

编译代码并运行测试。

```bash
mvn package
```

运行测试并生成 jar 文件。

```bash
mvn spring-boot:run
```

通过 Maven 启动 Spring Boot。

## 本节需要记住什么

1. Maven 管理依赖、编译、测试和打包。
2. `pom.xml` 是 Maven 项目的核心配置文件。
3. 修改 `pom.xml` 后要重新加载 Maven。
4. `starter-web` 提供 Web、Tomcat 和 JSON 能力。

理解这些概念后，进入[第 6 步：创建 Book 对象并返回 JSON](06-创建Book对象并返回JSON.md)。

