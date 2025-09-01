# Task Manager Demo

Spring Boot + MyBatis 7天计划的Day 1实现，一个简单的任务管理系统后端。

## 功能说明

- 任务CRUD操作（创建、查询、更新、删除）
- MySQL数据库集成
- RESTful API接口设计
- Spring Security基础配置

## 技术栈

- Spring Boot 2.7.0
- MyBatis 3.5.x
- MySQL 8.0
- Java 11+

## 项目结构

```
src/
├── main/
│   ├── java/com/example/taskmanager/
│   │   ├── entity/        # 实体类
│   │   ├── mapper/        # 数据访问层
│   │   ├── service/       # 业务逻辑层
│   │   ├── controller/    # API控制器
│   │   └── TaskManagerApplication.java  # 应用入口
│   └── resources/
│       ├── application.yml  # 应用配置
│       └── schema.sql       # 数据库脚本
└── test/                  # 单元测试
```

## 快速启动

### 环境要求

- JDK 11+
- MySQL 8.0
- Maven 3.6+

### 步骤

1. 克隆仓库
```bash
git clone <repository-url>
cd task-manager
```

2. 配置数据库
修改 `src/main/resources/application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/task_manager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: 你的数据库用户名
    password: 你的数据库密码
```

3. 构建并运行
```bash
mvn clean package
java -jar target/task-manager-0.0.1-SNAPSHOT.jar
```

4. 访问API
- 所有任务: GET http://localhost:8080/api/tasks
- 创建任务: POST http://localhost:8080/api/tasks
- 查看任务: GET http://localhost:8080/api/tasks/{id}
- 更新任务: PUT http://localhost:8080/api/tasks/{id}
- 删除任务: DELETE http://localhost:8080/api/tasks/{id}

## 许可证

MIT