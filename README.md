<div align="center">

# 🌟 伴伴 BanBan

### 年轻人兴趣社交平台

**⚠️ 当前为基础版本（单体架构），正在进行微服务改造和 AI 集成升级**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.4-4FC08D?style=flat-square&logo=vue.js)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0-DC382D?style=flat-square&logo=redis)](https://redis.io/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](LICENSE)

</div>

---

## 📢 项目状态

**当前版本：v1.0 基础版（单体架构）**

本项目已完成基础社交功能的开发，采用 Spring Boot 单体架构。后续将进行以下升级：

- 🔄 **微服务改造** - 拆分为多个独立服务
- 🤖 **AI 集成** - 接入 Dify 和 MCP 协议
- 📊 **服务治理** - 使用 Nacos 进行服务注册与配置管理

完整开发计划见 [项目路线图](#-开发路线图)

---

## 📖 项目简介

伴伴是一个基于 **Spring Boot 3 + Vue 3** 的社交平台，采用前后端分离架构，实现了用户系统、动态发布、点赞评论、关注系统等核心社交功能。

项目设计简洁易懂，代码结构清晰，适合作为全栈开发学习和面试作品展示。

> **📌 注意：** 当前为基础版本（单体架构），后续将进行微服务改造和 AI 集成升级，打造完整的企业级社交平台。

### ✨ 核心功能

- 👤 **用户系统** - 注册、登录、个人信息管理
- 📝 **动态广场** - 发布文字/图片动态，浏览所有内容
- ❤️ **互动功能** - 点赞、评论、分享
- 👥 **关注系统** - 关注/取消关注、关注列表、粉丝列表
- 🔔 **消息通知** - 系统通知（预留接口）

---

## 🛠️ 技术栈

### 后端
| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.2.0 | 核心框架 |
| MyBatis-Plus | 3.5.5 | ORM 框架 |
| Spring Security | 6.2.0 | 安全认证 |
| JWT | 0.12.3 | Token 认证 |
| MySQL | 8.0 | 数据库 |
| Redis | 7.0 | 缓存 |
| Maven | 3.6+ | 项目管理 |

### 前端
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.4.0 | 前端框架 |
| Vite | 5.0.0 | 构建工具 |
| Element Plus | 2.4.4 | UI 组件库 |
| Pinia | 2.1.7 | 状态管理 |
| Vue Router | 4.2.5 | 路由管理 |
| Axios | 1.6.2 | HTTP 客户端 |

### 后续升级技术栈（计划中）
| 技术 | 说明 |
|------|------|
| Spring Cloud | 微服务框架 |
| Nacos | 服务注册与配置中心 |
| Spring Cloud Gateway | API 网关 |
| OpenFeign | 服务间调用 |
| RabbitMQ | 消息队列 |
| Dify | AI 应用开发平台 |
| MCP | Model Context Protocol |
| Docker | 容器化部署 |
| Kubernetes | 容器编排 |

---

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0
- Redis 7.0
- Node.js 18+

### 1. 克隆项目

```bash
git clone https://github.com/your-username/banban.git
cd banban
```

### 2. 初始化数据库

```bash
# 登录 MySQL
mysql -uroot -p

# 创建数据库并执行初始化脚本
CREATE DATABASE banban CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE banban;
source sql/init.sql
```

### 3. 修改配置

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/banban?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password  # 修改为你的 MySQL 密码
  
  data:
    redis:
      host: localhost
      port: 6379
```

### 4. 启动后端

```bash
mvn spring-boot:run
```

或在 IDEA 中运行 `ZhiBanApplication.java`

启动成功后访问：
- API 文档：http://localhost:8080/swagger-ui.html

### 5. 启动前端

```bash
cd frontend
npm install
npm run dev
```

访问：http://localhost:5173

---

## 📁 项目结构

```
banban/
├── src/main/java/com/zhiban/
│   ├── ZhiBanApplication.java          # 启动类
│   ├── config/                          # 配置类
│   │   ├── SecurityConfig.java         # Spring Security 配置
│   │   ├── RedisConfig.java            # Redis 配置
│   │   ├── MyBatisPlusConfig.java      # MyBatis-Plus 配置
│   │   └── CorsConfig.java             # 跨域配置
│   ├── common/                          # 公共类
│   │   ├── Result.java                 # 统一返回结果
│   │   ├── PageResult.java             # 分页结果
│   │   ├── BusinessException.java      # 业务异常
│   │   └── GlobalExceptionHandler.java # 全局异常处理
│   ├── entity/                          # 实体类
│   │   ├── User.java
│   │   ├── Post.java
│   │   ├── Comment.java
│   │   ├── PostLike.java
│   │   ├── Follow.java
│   │   └── Notification.java
│   ├── mapper/                          # Mapper 接口
│   ├── service/                         # Service 业务层
│   ├── controller/                      # Controller 控制器
│   ├── dto/                             # 数据传输对象
│   └── util/                            # 工具类
│       └── JwtUtil.java
├── src/main/resources/
│   ├── application.yml                  # 配置文件
│   └── mapper/                          # MyBatis XML
├── sql/
│   └── init.sql                         # 数据库初始化脚本
├── frontend/                            # 前端项目
│   ├── src/
│   │   ├── api/                        # API 接口
│   │   ├── router/                     # 路由
│   │   ├── store/                      # 状态管理
│   │   ├── views/                      # 页面
│   │   ├── components/                 # 组件
│   │   └── utils/                      # 工具
│   ├── package.json
│   └── vite.config.js
└── pom.xml
```

---

## 📡 API 接口

### 认证相关
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/register` | 用户注册 |
| POST | `/api/auth/login` | 用户登录 |
| GET | `/api/auth/me` | 获取当前用户信息 |
| PUT | `/api/auth/me` | 更新用户信息 |

### 动态相关
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/post` | 发布动态 |
| GET | `/api/post/list` | 动态列表（分页） |
| GET | `/api/post/{id}` | 动态详情 |
| DELETE | `/api/post/{id}` | 删除动态 |
| POST | `/api/post/{id}/like` | 点赞/取消点赞 |

### 评论相关
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/comment` | 发表评论 |
| GET | `/api/comment/post/{postId}` | 评论列表 |
| DELETE | `/api/comment/{id}` | 删除评论 |

### 关注相关
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/follow/{userId}` | 关注/取消关注 |
| GET | `/api/follow/following` | 我的关注列表 |
| GET | `/api/follow/followers` | 我的粉丝列表 |
| GET | `/api/follow/stats/{userId}` | 关注统计 |

完整的 API 文档请访问：http://localhost:8080/swagger-ui.html

---

## 🔧 核心代码说明

### 1. 统一返回结果

所有接口返回统一的 `Result<T>` 格式：

```java
{
  "code": 200,
  "message": "操作成功",
  "data": { ... }
}
```

### 2. JWT 认证流程

1. 用户登录 → 后端生成 JWT Token
2. 前端保存 Token 到 localStorage
3. 每次请求通过 Axios 拦截器自动添加 `Authorization: Bearer <token>`
4. 后端 `JwtAuthFilter` 验证 Token，提取用户信息

### 3. 分页查询

使用 MyBatis-Plus 分页插件：

```java
Page<Post> page = new Page<>(pageNum, pageSize);
postMapper.selectPage(page, queryWrapper);
```

### 4. 点赞防重复

数据库唯一索引保证：

```sql
UNIQUE KEY `uk_post_user` (`post_id`, `user_id`)
```

---

## 🗺️ 开发路线图

### Phase 1: 基础版本 ✅ (已完成)
- [x] 用户注册登录
- [x] 发布动态
- [x] 动态列表
- [x] 点赞评论
- [x] 关注系统
- [x] 前后端分离架构

### Phase 2: 功能完善 🚧 (进行中)
- [ ] 图片上传与存储
- [ ] 消息通知系统
- [ ] 搜索功能
- [ ] 个人主页优化
- [ ] 响应式移动端适配

### Phase 3: 微服务改造 🔄 (计划中)
- [ ] 拆分为多个微服务
  - 用户服务 (User Service)
  - 动态服务 (Post Service)
  - 互动服务 (Interaction Service)
  - 消息服务 (Message Service)
- [ ] 引入 Spring Cloud 组件
- [ ] API 网关 (Gateway)
- [ ] 服务注册与发现 (Nacos)
- [ ] 配置中心 (Nacos)
- [ ] 分布式事务处理
- [ ] 服务间通信 (OpenFeign/RabbitMQ)

### Phase 4: AI 智能集成 🤖 (计划中)
- [ ] 集成 Dify AI 平台
- [ ] MCP (Model Context Protocol) 协议接入
- [ ] 智能内容推荐
- [ ] AI 内容审核
- [ ] 智能回复助手
- [ ] 用户行为分析

### Phase 5: 运维与监控 📊 (计划中)
- [ ] Docker 容器化部署
- [ ] Kubernetes 编排
- [ ] 链路追踪 (SkyWalking/Zipkin)
- [ ] 日志聚合 (ELK Stack)
- [ ] 性能监控 (Prometheus + Grafana)
- [ ] CI/CD 自动化部署

---

## 🎯 技术架构演进

### 当前架构（v1.0）
```
前端 (Vue 3) → Nginx → Spring Boot 单体应用 → MySQL + Redis
```

### 目标架构（v2.0）
```
前端 (Vue 3) 
  ↓
API Gateway (Spring Cloud Gateway)
  ↓
┌─────────────┬─────────────┬─────────────┬─────────────┐
│ User Service│ Post Service│ Interaction │ AI Service  │
│             │             │   Service   │  (Dify+MCP) │
└─────────────┴─────────────┴─────────────┴─────────────┘
  ↓             ↓             ↓             ↓
MySQL         MySQL         MySQL        MongoDB
  ↓             ↓             ↓             ↓
         Nacos (服务注册/配置中心)
         ↓
    Redis Cluster
```

---

## 💡 学习要点

### 后端重点
1. **Spring Security 配置** - `SecurityConfig.java`
2. **JWT 生成和验证** - `JwtUtil.java`
3. **MyBatis-Plus 使用** - 各 Service 类
4. **统一异常处理** - `GlobalExceptionHandler.java`

### 前端重点
1. **Axios 拦截器** - `utils/request.js`
2. **Pinia 状态管理** - `store/user.js`
3. **Vue Router 路由守卫** - `router/index.js`
4. **组件化开发** - `components/PostCard.vue`

---

## 🐛 常见问题

**Q: 数据库连接失败？**  
A: 检查 MySQL 是否启动，数据库名和密码是否正确

**Q: Redis 连接失败？**  
A: 检查 Redis 是否启动，端口是否正确

**Q: 前端请求 401？**  
A: 检查 Token 是否正确传递，是否过期

**Q: 跨域问题？**  
A: 已配置 `CorsConfig.java`，检查前端代理配置

---

## 📚 相关资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Vue 3 官方文档](https://vuejs.org/)
- [MyBatis-Plus 文档](https://baomidou.com/)
- [Element Plus 文档](https://element-plus.org/)

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

如果你对这个项目感兴趣，想要参与后续的微服务改造或 AI 集成，请联系作者。

---

## 📧 联系方式

- GitHub: [@LYJ-0410](https://github.com/LYJ-0410)
- Email: [your-email@example.com]

---

## 📄 许可证

MIT License

---

<div align="center">

### 🌟 伴伴 BanBan

**当前版本：v1.0 基础版** | **状态：持续开发中**

简洁易懂的社交应用 → 企业级微服务平台

Made with ❤️ for learning

---

**⭐ 如果这个项目对你有帮助，请给一个 Star 支持！**

</div>
