-- 创建任务表
CREATE TABLE IF NOT EXISTS task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'TODO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入测试数据
INSERT INTO task (title, status) VALUES 
('完成Spring Boot项目初始化', 'DONE'),
('配置MySQL数据源', 'DONE'),
('创建Task实体类', 'DONE'),
('实现Task CRUD接口', 'IN_PROGRESS'),
('编写单元测试', 'TODO');