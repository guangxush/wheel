# wheel - Java 技术全栈实践项目

> 一个面向开发者的开源学习仓库，聚合 Java 高可用、网络、并发、ORM、RPC 等核心项目实战。

- ✅ 精心分类，内容清晰，适合快速查阅与沉淀。
- ✅ 纯 Java 实现，无须复杂依赖即可学习源码逻辑。
- ✅ 多模块示例可直接导入 IDE 运行。

## 核心模块一览

| 模块 | 说明 | 目录 |
|---|---|---|
| JSONParse | 自定义 JSON 解析器实现 | [JSONParse/README.md](JSONParse/README.md) |
| MQ | 自研消息队列实现 | [MQ/README.md](MQ/README.md) |
| Netty | 基于 Netty 的网络模型 | [Netty/README.md](Netty/README.md) |
| NIO | IO 多种模型实现与对比 | [NIO/README.md](NIO/README.md) |
| ORM | 根据 SQL 生成 Java 实体与绑定 | [ORM/README.md](ORM/README.md) |
| PageReplace | CPU缓存置换算法 | [PageReplace/README.md](PageReplace/README.md) |
| RPC | 简易 RPC 框架 | [RPC/README.md](RPC/README.md) |
| ThreadPool | 自定义线程池实现 | [ThreadPool/README.md](ThreadPool/README.md) |
| ToYaml | Properties 转 YAML 工具 | [ToYaml/README.md](ToYaml/README.md) |
| Cache | ThreadLocal 查询缓存 | [Cache/README.md](Cache/README.md) |
| Instrument | Java Instrument Agent 实现 | [Instrument/README.md](Instrument/README.md) |
| Annotation | 注解处理与元编程实践 | [Annotation/README.md](Annotation/README.md) |
| SpringCore | Spring 底层机制解析 | [SpringCore/README.md](SpringCore/README.md) |
| HappyParse | 各类解析小工具集合 | [HappyParse/README.md](HappyParse/README.md) |
| PlayCenter | 业务算法练习（如抢红包） | [PlayCenter/README.md](PlayCenter/README.md) |
| JVM | JVM 实验探索 | [JVM/README.md](JVM/README.md) |
| Factory | Spring 工厂模式实现 | [Factory/README.md](Factory/README.md) |
| Template | Spring 模板方法实现 | [Template/README.md](Template/README.md) |
| HighAvailable | 高并发处理策略与实现 | [HighAvailable/README.md](HighAvailable/README.md) |

## 快速开始

1. 克隆仓库：

```bash
git clone https://github.com/guangxush/wheel.git
cd wheel
```

2. 导入 IDE：
  - 如果 IDEA 没识别项目，右键 `pom.xml` -> `Add as Maven Project`。

3. 运行模块示例
  - 例如：`mvn -pl JSONParse test`（或分别进入子模块执行）

## 贡献指南

- 欢迎提 PR、Issue。请保证代码风格一致、含单测。
- 目标：让更多开发者通过阅读源码提升技术能力。

## 推荐关注

- 如果你喜欢请 ⭐️ Star，转发给更多开发者。
- 微信公众号：`划小船`（持续发布高质量技术文章与实战案例）。

---

**快速入口**

- [设计模式参考](https://github.com/guangxush/DesignPatterns)

> 你可以从上面推荐模块选一个开始，3~5 天快速读懂 Java 关键技术！


