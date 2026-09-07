import { defineConfig } from 'rspress/config';

export default defineConfig({
  // 文档源目录，直接复用仓库里的 docs
  root: 'docs',
  // 构建产物目录，已加入 .gitignore
  outDir: 'doc_build',
  lang: 'zh',
  title: 'Spring Boot 图书 API 教程',
  description: '使用 Spring Boot 2.7.18 与 Java 8 构建图书管理 REST 接口的入门教程',
  // 本地预览不部署，站点挂在根路径
  base: '/',
  themeConfig: {
    outlineTitle: '本页目录',
    prevPageText: '上一篇',
    nextPageText: '下一篇',
    searchPlaceholderText: '搜索文档',
    lastUpdated: true,
    lastUpdatedText: '最后更新',
    nav: [
      { text: '学习目录', link: '/README' },
      { text: '如何重现代码', link: '/00-如何重现每一节代码' },
      { text: 'curl 验证', link: '/使用curl验证接口' },
    ],
    // docs 下的文档是平铺的，侧边栏在这里按阶段显式声明
    sidebar: {
      '/': [
        {
          text: '开始之前',
          collapsed: false,
          items: [
            { text: '学习目录', link: '/README' },
            { text: '如何重现每一节代码', link: '/00-如何重现每一节代码' },
            { text: '使用 curl 验证接口', link: '/使用curl验证接口' },
          ],
        },
        {
          text: '第一阶段 认识并启动 Spring Boot',
          collapsed: false,
          items: [
            { text: '01 检查开发环境', link: '/01-检查开发环境' },
            { text: '02 使用 IDEA 导入项目', link: '/02-使用IDEA导入项目' },
            { text: '03 启动项目并访问接口', link: '/03-启动项目并访问接口' },
            { text: '04 认识项目结构', link: '/04-认识项目结构' },
          ],
        },
        {
          text: '第二阶段 Maven 配置与 JSON',
          collapsed: false,
          items: [
            { text: '05 理解 Maven 和 pom.xml', link: '/05-理解Maven和pom.xml' },
            { text: '06 创建 Book 对象并返回 JSON', link: '/06-创建Book对象并返回JSON' },
          ],
        },
        {
          text: '第三到第五阶段 增删改查',
          collapsed: false,
          items: [
            { text: '07 使用 List 查询全部图书', link: '/07-使用List查询全部图书' },
            { text: '08 使用 POST 新增图书', link: '/08-使用POST新增图书' },
            { text: '09 使用 PUT 修改图书', link: '/09-使用PUT修改图书' },
            { text: '10 使用 DELETE 删除图书', link: '/10-使用DELETE删除图书' },
          ],
        },
        {
          text: '第六阶段 代码分层',
          collapsed: false,
          items: [
            {
              text: '11 Controller、Service、Repository 分层',
              link: '/11-Controller-Service-Repository分层',
            },
          ],
        },
        {
          text: '第七阶段 接入数据库',
          collapsed: false,
          items: [
            {
              text: '12 使用 H2 数据库和 Spring Data JPA',
              link: '/12-使用H2数据库和SpringDataJPA',
            },
          ],
        },
        {
          text: '第八阶段 参数校验和错误处理',
          collapsed: false,
          items: [
            { text: '13 参数校验和统一异常处理', link: '/13-参数校验和统一异常处理' },
          ],
        },
        {
          text: '第九阶段 自动化测试',
          collapsed: false,
          items: [
            { text: '14 为接口补上自动化测试', link: '/14-为接口补上自动化测试' },
          ],
        },
      ],
    },
  },
});
