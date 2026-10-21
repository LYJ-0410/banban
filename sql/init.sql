-- ========================================
-- 智伴星 ZhiBanStar - 数据库初始化脚本
-- ========================================

CREATE DATABASE IF NOT EXISTS zhibanstar DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE zhibanstar;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
  `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 动态表
CREATE TABLE IF NOT EXISTS `post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '动态ID',
  `user_id` BIGINT NOT NULL COMMENT '发布者ID',
  `content` TEXT NOT NULL COMMENT '内容',
  `images` JSON DEFAULT NULL COMMENT '图片（JSON数组）',
  `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论数',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-草稿，1-已发布',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态表';

-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL COMMENT '动态ID',
  `user_id` BIGINT NOT NULL COMMENT '评论者ID',
  `reply_user_id` BIGINT DEFAULT NULL COMMENT '被回复用户ID',
  `content` VARCHAR(500) NOT NULL COMMENT '评论内容',
  `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_post_id` (`post_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 点赞表
CREATE TABLE IF NOT EXISTS `post_like` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL COMMENT '动态ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_post_user` (`post_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

-- 关注表
CREATE TABLE IF NOT EXISTS `follow` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `follower_id` BIGINT NOT NULL COMMENT '粉丝用户ID',
  `following_id` BIGINT NOT NULL COMMENT '被关注用户ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_follower_following` (`follower_id`, `following_id`),
  KEY `idx_following_id` (`following_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注表';

-- 通知消息表
CREATE TABLE IF NOT EXISTS `notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
  `from_user_id` BIGINT NOT NULL COMMENT '发送用户ID',
  `type` VARCHAR(20) NOT NULL COMMENT '类型：like/comment/follow',
  `target_id` BIGINT DEFAULT NULL COMMENT '关联ID',
  `content` VARCHAR(255) NOT NULL COMMENT '消息内容',
  `read_status` TINYINT NOT NULL DEFAULT 0 COMMENT '0-未读，1-已读',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_read_status` (`read_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知消息表';

-- ========================================
-- 插入测试数据
-- ========================================

-- 测试用户（密码是 123456 的 BCrypt 加密）
INSERT INTO `user` (`username`, `password`, `nickname`, `avatar`, `gender`, `bio`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '管理员', '/uploads/avatar/default.png', 1, '系统管理员'),
('zhangsan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '张三', '/uploads/avatar/default.png', 1, '热爱生活的张三'),
('lisi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '李四', '/uploads/avatar/default.png', 2, '喜欢旅行的李四');

-- 测试动态
INSERT INTO `post` (`user_id`, `content`, `images`, `like_count`, `comment_count`) VALUES
(1, '大家好，我是管理员！欢迎来到智伴星~', '["/uploads/post/test1.jpg"]', 10, 5),
(2, '今天天气真好，出去走走吧！', '["/uploads/post/test2.jpg"]', 8, 3),
(3, '分享一本好书《人类简史》', '[]', 15, 7);
