# 使用 curl 验证 Spring Boot 接口

## 这篇文档解决什么问题

浏览器地址栏只能方便地发送 GET 请求，而新增、修改和删除接口还需要使用 POST、PUT、DELETE 等请求方法。

当前学习环境中的 IDEA HTTP Client 需要许可证。这个限制只影响 IDEA 内置的请求工具，不影响 Java、Maven、Spring Boot 或已经编写的接口。

本课程后续统一使用 macOS 自带的 `curl` 验证接口：

- 不需要安装新软件。
- 不需要 IDEA HTTP Client 许可证。
- 命令可以直接复制，方便重现每一步。
- 能看到 HTTP 状态码、响应头和 JSON 响应。

项目中的 `requests/book-api.http` 继续保留，用来集中记录请求地址和 JSON 示例，但当前不要求通过 IDEA 运行它。

## 1. curl 是什么

`curl` 是一个在终端中发送网络请求的命令行工具。可以把它理解成一个能够指定请求方法、请求头和请求体的“命令行浏览器”。

macOS 通常已经自带 `curl`。打开 IDEA 底部 **Terminal** 或 macOS 的“终端”，执行：

```bash
curl --version
```

只要能看到版本信息，就表示可以使用。

## 2. 使用前先启动 Spring Boot

发送任何请求前，先确认 `BookApiApplication` 正在运行。

IDEA 底部 Run 窗口应该能看到：

```text
Started BookApiApplication
```

如果应用没有运行，`curl` 可能显示：

```text
Failed to connect to localhost port 8080
```

这时需要启动项目，不是修改 `curl` 命令。

## 3. GET：查询全部图书

执行：

```bash
curl -i http://localhost:8080/api/books
```

这里没有写 `-X GET`，因为 `curl` 在没有请求体时默认使用 GET。

参数说明：

- `curl`：运行请求工具。
- `-i`：把 HTTP 状态和响应头一起显示出来。
- 最后的地址：要访问的接口。

成功时，前面能看到：

```text
HTTP/1.1 200
Content-Type: application/json
```

后面是响应体。

从第 16 节开始，所有 `/api/books` 接口的响应都是同一个结构：

```json
{"code":0,"msg":"成功","data":[{"id":1,"title":"Spring Boot 入门","author":"张三"}]}
```

`code` 为 0 表示业务成功，图书数据在 `data` 里。第 16 节之前的文档里看到的是不带这层外壳的裸 JSON，那是当时的写法，两者都属于正常，只要和你当前代码所在的节次对得上。

## 4. GET：根据编号查询图书

执行：

```bash
curl -i http://localhost:8080/api/books/1
```

地址末尾的 `1` 是图书编号。成功响应示例：

```json
{"code":0,"msg":"成功","data":{"id":1,"title":"Spring Boot 入门","author":"张三"}}
```

## 5. POST：新增图书

执行下面这一整行：

```bash
curl -i -X POST http://localhost:8080/api/books -H 'Content-Type: application/json' -d '{"title":"Spring 实战","author":"赵六"}'
```

新增参数说明：

- `-X POST`：指定 HTTP 请求方法是 POST。
- `-H 'Content-Type: application/json'`：告诉 Spring 请求体是 JSON。
- `-d '...'`：指定要发送的请求数据。

成功状态是：

```text
HTTP/1.1 200
```

响应示例：

```json
{"code":0,"msg":"成功","data":{"id":4,"title":"Spring 实战","author":"赵六"}}
```

`data.id` 是数据库生成的编号。第 8 节到第 15 节期间，新增成功返回的是 `201 Created`，第 16 节起统一改为 200，用 `code` 表示业务结果。

## 6. PUT：修改图书

执行下面这一整行：

```bash
curl -i -X PUT http://localhost:8080/api/books/1 -H 'Content-Type: application/json' -d '{"title":"Spring Boot 进阶","author":"张三"}'
```

这条命令表示：

1. 使用 PUT 请求。
2. 修改地址末尾编号为 1 的图书。
3. 请求体使用 JSON。
4. 把书名改成“Spring Boot 进阶”，作者设置为“张三”。

成功状态是：

```text
HTTP/1.1 200
```

响应示例：

```json
{"code":0,"msg":"成功","data":{"id":1,"title":"Spring Boot 进阶","author":"张三"}}
```

修改后可以继续查询确认：

```bash
curl -i http://localhost:8080/api/books/1
```

## 7. DELETE：删除图书

执行：

```bash
curl -i -X DELETE http://localhost:8080/api/books/2
```

这条命令表示删除编号为 2 的图书。DELETE 请求不需要 JSON，因此没有 `Content-Type` 和 `-d` 参数。

成功状态是：

```text
HTTP/1.1 200
```

响应示例：

```json
{"code":0,"msg":"成功","data":null}
```

删除成功不需要返回数据，所以 `data` 是 `null`。第 10 节到第 15 节期间，删除成功返回的是 `204 No Content`，没有响应正文，第 16 节起统一改为 200 加上面这个结构。

删除后查询全部图书进行确认：

```bash
curl -i http://localhost:8080/api/books
```

## 8. 为什么 JSON 外面使用单引号

命令中的请求体是：

```bash
'{"title":"Spring Boot 进阶","author":"张三"}'
```

最外层的单引号由终端处理，里面的英文双引号属于 JSON。

这样 JSON 可以原样交给 `curl`，不需要给每一个双引号增加额外转义。

注意：必须使用英文半角的单引号和双引号，不能使用中文弯引号。

## 9. 为什么响应 JSON 后面出现百分号

有时终端显示为：

```text
{"code":0,"msg":"成功","data":{"id":4,"title":"Spring 实战","author":"赵六"}}%
```

末尾的 `%` 不是服务器返回的 JSON 内容，也不是程序错误。

原因是响应内容末尾没有换行，macOS 默认使用的 zsh 通过 `%` 提示“上一段输出没有以换行结束”。真正的 JSON 在 `%` 之前已经结束。

## 10. 一条命令写成多行

命令太长时，可以在行尾使用反斜杠 `\`：

```bash
curl -i -X PUT http://localhost:8080/api/books/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Spring Boot 进阶","author":"张三"}'
```

行尾的 `\` 表示命令还没有结束，下一行继续。反斜杠后面不要再添加空格。

对于刚开始使用终端的学习者，直接复制文档提供的单行命令最简单。

## 11. 常见错误

### Failed to connect to localhost port 8080

Spring Boot 没有运行，或者启动失败。回到 IDEA Run 窗口检查应用状态。

### 405 Method Not Allowed

请求方法不正确。例如修改接口需要 `-X PUT`，新增接口需要 `-X POST`。

### 415 Unsupported Media Type

缺少 JSON 请求头。确认命令中包含：

```text
-H 'Content-Type: application/json'
```

### 400 Bad Request

通常是 JSON 格式错误。检查：

- 属性名和字符串使用英文双引号。
- 属性之间有英文逗号。
- 最外层使用英文单引号。
- 大括号成对出现。

### curl 执行后数据没有变化

先查看 HTTP 状态码，再确认访问地址和请求方法正确。还要确认请求后没有重启应用，因为当前数据只保存在内存中。

## 12. 截图时的隐私注意事项

培训文档截图只需要保留 HTTP 状态和 JSON 结果。截图前应检查终端或控制台中是否出现：

- 用户名和本地文件路径
- Cookie、Token 或 Authorization 请求头
- 内部仓库地址
- 其他与课程无关的信息

发现这些内容时，可以缩小截图范围，只保留接口验证结果。
