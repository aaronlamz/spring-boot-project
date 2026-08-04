# 第 6 步：创建 Book 对象并返回 JSON

## 这一节要完成什么

创建一个 `Book` Java 类和一个查询接口。访问接口时，Spring Boot 会把 `Book` 对象自动转换成 JSON。

完成后的请求地址：

```text
GET http://localhost:8080/api/books/1
```

预期响应：

```json
{
  "id": 1,
  "title": "Spring Boot 入门",
  "author": "张三"
}
```

## 1. 停止正在运行的项目

修改代码前，先在 IDEA 底部 Run 窗口点击红色方块，停止当前程序。

## 2. 创建 model 包

1. 在 IDEA 左侧找到 `src/main/java/com.example.bookapi`。
2. 右键点击 `com.example.bookapi`。
3. 选择 **New → Package**。
4. 输入：

   ```text
   model
   ```

5. 按回车。

完整包名会是 `com.example.bookapi.model`。`model` 用来存放描述业务数据的 Java 类。

## 3. 创建 Book 类

1. 右键点击刚创建的 `model` 包。
2. 选择 **New → Java Class**。
3. 输入类名 `Book`。
4. 按回车。

将内容写成：

```java
package com.example.bookapi.model;

public class Book {

    private Long id;
    private String title;
    private String author;

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
```

下面逐段解释这份代码。

```java
package com.example.bookapi.model;
```

声明 `Book` 位于 `model` 包。`model` 不是 Spring 强制规定的名称，而是常用的分层命名，表示这里存放描述业务数据的类。

```java
public class Book {
```

声明一个公开的 `Book` 类。类可以理解为“对象的设计图”，规定一本图书包含哪些数据和操作。

```java
private Long id;
private String title;
private String author;
```

三个成员变量分别表示图书编号、书名和作者。

- `private`：只能在 `Book` 类内部直接访问，外部通过 Getter 和 Setter 操作。
- `Long`：Java 的长整数包装类型，可以表示编号，也允许值为 `null`。
- `String`：Java 的字符串类型。
- `id`、`title`、`author`：变量名。

```java
public Book(Long id, String title, String author) {
    this.id = id;
    this.title = title;
    this.author = author;
}
```

这是构造方法：

- 构造方法名与类名相同。
- 构造方法没有返回类型，连 `void` 也不写。
- `this.id` 表示当前对象的成员变量。
- 右侧的 `id` 表示传入构造方法的参数。
- `this.id = id` 把参数保存到当前对象中。

构造方法用于创建并初始化对象：

```java
new Book(1L, "Spring Boot 入门", "张三")
```

Getter 用于读取字段，Setter 用于修改字段。Jackson 会调用 Getter 获取数据，再生成 JSON 属性。

以 `id` 为例：

```java
public Long getId() {
    return id;
}
```

`getId()` 返回当前对象的 `id`。Jackson 发现 `getId()` 后，会生成名为 `id` 的 JSON 属性。

```java
public void setId(Long id) {
    this.id = id;
}
```

`setId()` 接收一个新编号并保存。`void` 表示这个方法不返回结果。`title` 和 `author` 的 Getter、Setter 作用相同。

IDEA 也可以自动生成这些方法：在类内部右键，选择 **Generate → Constructor** 或 **Getter and Setter**。

## 4. 创建 controller 包

1. 右键点击 `com.example.bookapi`。
2. 选择 **New → Package**。
3. 输入 `controller` 并按回车。

完整包名是 `com.example.bookapi.controller`。

## 5. 创建 BookController

1. 右键点击 `controller` 包。
2. 选择 **New → Java Class**。
3. 输入 `BookController`。
4. 按回车。

写入：

```java
package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return new Book(id, "Spring Boot 入门", "张三");
    }
}
```

下面逐行理解 Controller。

```java
package com.example.bookapi.controller;
```

声明类位于 `controller` 包。这个包仍然是 `com.example.bookapi` 的子包，所以启动时能被 Spring 扫描。

```java
import com.example.bookapi.model.Book;
```

`BookController` 与 `Book` 不在同一个包，因此需要导入 `Book` 类。

其余 `import` 导入 Spring MVC 提供的四个注解。

```java
@RestController
```

让 Spring 创建并管理一个 `BookController` 对象，同时把方法返回值写入 HTTP 响应。

```java
@RequestMapping("/api/books")
```

为这个类中的所有接口设置统一地址前缀。

```java
public class BookController {
```

声明 Controller 类。应用启动时，不需要在 `main` 方法中手工执行 `new BookController()`；Spring 会创建它。

```java
@GetMapping("/{id}")
```

表示这个方法处理 HTTP GET 请求，`{id}` 是地址中会变化的部分。

```java
public Book findById(@PathVariable Long id) {
```

- `public`：Spring 可以调用这个方法。
- `Book`：方法返回一个 `Book` 对象。
- `findById`：方法名，表达“根据编号查询”。
- `@PathVariable`：从请求路径读取值。
- `Long id`：把路径中的文字转换成 `Long`，保存到参数 `id`。

```java
return new Book(id, "Spring Boot 入门", "张三");
```

- `new Book(...)`：调用构造方法，在内存中创建一个 `Book` 对象。
- 第一个参数使用地址中传入的 `id`。
- 当前阶段还没有数据库，所以书名和作者暂时写成固定值。
- `return`：把创建好的对象交回 Spring MVC。

## 6. 理解请求地址

类上的配置是：

```java
@RequestMapping("/api/books")
```

方法上的配置是：

```java
@GetMapping("/{id}")
```

组合后的地址是：

```text
/api/books/{id}
```

`{id}` 是路径变量。访问 `/api/books/1` 时，`@PathVariable` 会把字符串 `1` 转换成 `Long`，传给方法参数 `id`。

## 7. 启动并验证

1. 打开 `BookApiApplication.java`。
2. 点击 `main` 左侧绿色三角形。
3. 选择 **Run 'BookApiApplication'**。
4. 等待日志出现 `Started BookApiApplication`。
5. 浏览器访问：

   ```text
   http://localhost:8080/api/books/1
   ```

浏览器通常会把 JSON 显示在一行：

```json
{"id":1,"title":"Spring Boot 入门","author":"张三"}
```

把地址最后的 `1` 改成 `20`：

```text
http://localhost:8080/api/books/20
```

返回值中的 `id` 也会变成 `20`，说明路径变量已经传入 Controller。

## 8. Java 对象为什么会变成 JSON

Controller 方法声明返回 `Book`：

```java
public Book findById(...)
```

由于类上使用了 `@RestController`，Spring MVC 会把方法返回值写入 HTTP 响应。项目中的 Jackson 会读取 `Book` 的 Getter，将对象转换为 JSON。

这个过程不需要手工拼接 JSON 字符串。

## 9. 一次请求的完整执行过程

以访问 `http://localhost:8080/api/books/20` 为例：

```text
1. 浏览器连接 localhost 的 8080 端口
                    ↓
2. Tomcat 收到 GET /api/books/20
                    ↓
3. Spring MVC 将它匹配到 BookController.findById
                    ↓
4. @PathVariable 取出字符串 "20"
                    ↓
5. Spring 将 "20" 转换成 Long 类型的 20
                    ↓
6. Java 执行 new Book(20, "Spring Boot 入门", "张三")
                    ↓
7. findById 返回 Book 对象
                    ↓
8. Jackson 调用 getId、getTitle、getAuthor
                    ↓
9. Jackson 生成 JSON
                    ↓
10. Spring MVC 将 JSON 写入 HTTP 响应
                    ↓
11. 浏览器显示响应内容
```

当前数据只存在于这次请求的内存对象中。请求结束后没有把图书保存到任何地方；后面的课程会先使用集合保存，再接入数据库。

## 成功标准

- 项目能够正常启动
- `/api/books/1` 返回 JSON
- 修改地址中的编号后，响应 `id` 同步变化
- 能说明 `@PathVariable` 的作用

## 常见问题

### BookController 找不到 Book

确认文件顶部存在：

```java
import com.example.bookapi.model.Book;
```

并确认 `Book.java` 的 package 是：

```java
package com.example.bookapi.model;
```

### 浏览器返回 404

确认访问地址包含完整前缀：

```text
/api/books/1
```

同时确认 `BookController` 位于 `com.example.bookapi` 的子包中，否则启动类默认无法扫描到它。

### 返回 id，但没有 title 和 author

确认 `Book` 类中存在三个 public Getter。Jackson 主要通过 Getter 获取属性值。
