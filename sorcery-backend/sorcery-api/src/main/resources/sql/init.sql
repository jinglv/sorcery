DROP DATABASE IF EXISTS `sorcery`;
CREATE DATABASE `sorcery` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# t_user用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`                        BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name`                 VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `password`                  VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `email`                     VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
    `salt`                      VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐值',
    `create_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT ='用户表';

# t_user_info用户信息表
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`
(
    `id`                        BIGINT  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`                   BIGINT  NOT NULL COMMENT '用户id',
    `nick`                      VARCHAR(100)  DEFAULT NULL COMMENT '昵称',
    `avatar`                    VARCHAR(255)  DEFAULT NULL COMMENT '头像',
    `sign`                      TEXT COMMENT '签名',
    `gender`                    VARCHAR(2)  DEFAULT NULL COMMENT '性别：0男 1女 2未知',
    `birth`                     VARCHAR(20)  DEFAULT NULL COMMENT '生日',
    `create_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT ='用户信息表';

# t_refresh_token用户token信息表
DROP TABLE IF EXISTS `t_refresh_token`;
CREATE TABLE `t_refresh_token`
(
    `id`                        BIGINT  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`                   BIGINT  NOT NULL COMMENT '用户id',
    `refresh_token`             VARCHAR(1280)  DEFAULT NULL COMMENT '刷新token',
    `create_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT ='用户token信息表';

# t_projects项目管理表
DROP TABLE IF EXISTS `t_projects`;
CREATE TABLE `t_projects`
(
    `id`                        BIGINT  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `project_name`              VARCHAR(50)  NOT NULL COMMENT '项目名称',
    `description`               VARCHAR(1280) DEFAULT NULL COMMENT '项目描述',
    `image`                     VARCHAR(255)  DEFAULT NULL COMMENT '项目图片',
    `is_delete`                 TINYINT(1)  DEFAULT 0 COMMENT '状态，0-未删除 1-已删除',
    `user_id`                   BIGINT  NOT NULL COMMENT '用户id',
    `create_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT ='项目管理表';