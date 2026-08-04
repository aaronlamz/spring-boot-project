# 第 2 步：使用 IDEA 导入项目

## 这一节要完成什么

用 IDEA 打开项目，让 IDEA 识别 `pom.xml` 并下载 Spring Boot 依赖。

开始前，请先确认已经获取本项目源码，并记住源码所在目录。项目根目录中应该包含 `pom.xml`。

## 1. 打开项目

如果 IDEA 停留在欢迎页：

1. 点击 **Open**。
2. 在文件选择窗口找到源码所在目录。
3. 选中包含 `pom.xml` 的项目根目录。
4. 点击 **Open**。
5. 如果出现“是否信任该项目”，确认源码来源可信后点击 **Trust Project**。

如果 IDEA 已经打开了其他项目：

1. 点击顶部菜单 **File**。
2. 点击 **Open**。
3. 选择同一个项目目录。
4. 如果询问打开方式，选择 **New Window**，避免关闭原来的窗口。

注意：选择的是整个 `spring-boot-project` 文件夹，不是单独选择 `pom.xml`，也不是选择 `src` 文件夹。

## 2. 等待 Maven 导入

IDEA 打开后，左侧 Project 面板应该能看到：

```text
spring-boot-project
├── docs
├── src
├── pom.xml
└── README.md
```

`pom.xml` 是 Maven 的项目配置文件。IDEA 会读取它并下载 Spring Boot 所需的 jar 包。

第一次下载可能持续几分钟。观察 IDEA 底部状态栏，等待下载和索引结束。

如果右下角出现 **Load Maven Changes**，点击它。

如果项目没有被识别为 Maven 项目：

1. 在左侧找到 `pom.xml`。
2. 右键点击 `pom.xml`。
3. 点击 **Add as Maven Project**。

## 3. 设置项目 Java 版本

1. 点击顶部菜单 **File**。
2. 点击 **Project Structure**。
3. 左侧选择 **Project**。
4. 将 **SDK** 或 **Project SDK** 选择为 Corretto 1.8。
5. 将 **Language level** 选择为 `8 - Lambdas, type annotations etc.`。
6. 点击 **Apply**，再点击 **OK**。

如果 SDK 下拉框中没有 Java 8：

1. 点击 **Add SDK**。
2. 选择 **JDK**。
3. 选择本机 JDK 8 的安装目录。
4. 确认添加，然后选择这个 SDK。

如果不知道 JDK 8 的安装目录，可以在 macOS 终端执行：

```bash
/usr/libexec/java_home -v 1.8
```

将命令输出的目录选为 JDK 目录即可。

## 4. 设置 Maven 使用的 Java

macOS 上打开设置：

1. 点击顶部菜单 **IntelliJ IDEA**。
2. 点击 **Settings**。
3. 依次展开 **Build, Execution, Deployment → Build Tools → Maven → Runner**。
4. 将 **JRE** 选择为 Project JDK 或 Corretto 1.8。
5. 点击 **Apply**，再点击 **OK**。

不同 IDEA 版本的菜单文字可能略有差异。找不到时，可以在 Settings 左上角搜索框输入 `Maven Runner`。

## 5. 在 IDEA 终端再次确认

点击 IDEA 底部的 **Terminal**。如果没有看到，可以通过顶部菜单 **View → Tool Windows → Terminal** 打开。

输入：

```bash
mvn -version
```

确认输出中的 Java version 是 `1.8.0_502`。

## 成功标准

- 左侧能看到 `pom.xml` 和 `src` 目录
- IDEA 没有一直显示正在下载或索引
- Project SDK 是 Java 8
- Maven Runner 使用 Java 8
- `pom.xml` 没有红色错误

完成后进入[第 3 步：启动项目并访问接口](03-启动项目并访问接口.md)。

## 常见问题

### pom.xml 全是红色

通常是 Maven 依赖还没有下载完成。先等待下载；然后打开右侧 Maven 工具窗口，点击重新加载按钮（两个循环箭头）。

### Maven 下载很慢

首次导入需要下载 Spring Boot、Tomcat 和测试工具等依赖，文件较多。只要底部仍有下载进度，可以继续等待，不要反复关闭 IDEA。

### 找不到 Project 面板

点击顶部菜单 **View → Tool Windows → Project**，或者按 `Command + 1`。
