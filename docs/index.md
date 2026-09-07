---
pageType: home

hero:
  name: Spring Boot 图书 API 教程
  text: 从环境准备到可持久化的 REST 接口
  tagline: 基于 Spring Boot 2.7.18 与 Java 8，用一个图书管理接口贯穿全部内容
  actions:
    - theme: brand
      text: 按顺序开始学习
      link: /README
    - theme: alt
      text: 如何重现每一节代码
      link: /00-如何重现每一节代码

features:
  - title: 环境与项目结构
    details: 检查 JDK 与 Maven，用 IDEA 导入并启动项目，认识 Spring Boot 的目录结构和 pom.xml。
    icon: 🧱
  - title: REST 接口增删改查
    details: 用 GET、POST、PUT、DELETE 完成图书的查询、新增、修改和删除，理解 JSON 与 HTTP 状态码。
    icon: 🔁
  - title: 分层与数据持久化
    details: 拆分 Controller、Service、Repository，接入 H2 与 Spring Data JPA，数据在重启后依然保留。
    icon: 🗄️
  - title: 健壮性与自动化测试
    details: 加入参数校验与统一异常处理，并为三层代码补上可重复执行的自动化测试。
    icon: 🧪
---
