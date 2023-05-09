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

# t_modules模块管理表
DROP TABLE IF EXISTS `t_modules`;
CREATE TABLE `t_modules`
(
    `id`                        BIGINT  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `module_name`               VARCHAR(50)  NOT NULL COMMENT '模块名称',
    `module_parent_id`          BIGINT  NOT NULL COMMENT '模块上级id',
    `is_delete`                 TINYINT(1)  DEFAULT 0 COMMENT '状态，0-未删除 1-已删除',
    `user_id`                   BIGINT  NOT NULL COMMENT '用户id',
    `create_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT ='模块管理表';

# t_apis接口信息管理表
DROP TABLE IF EXISTS `t_apis`;
CREATE TABLE `t_apis`
(
    `id`                        BIGINT  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `api_name`                  VARCHAR(50)  NOT NULL COMMENT '接口名称',
    `api_path`                  BIGINT  NOT NULL COMMENT '接口地址',
    `method`                    VARCHAR(20)  NOT NULL COMMENT '接口请求方法',
    `header`                    VARCHAR(200)  NOT NULL COMMENT '接口请求头',
    `params_type`               VARCHAR(20)  NOT NULL COMMENT '接口请求参数类型',
    `params_body`               VARCHAR(200)  NOT NULL COMMENT '接口请求主体',
    `response`                  VARCHAR(225)  NOT NULL COMMENT '接口请求主体',
    `assert_type`               VARCHAR(20)  NOT NULL COMMENT '断言类型',
    `assert_text`               VARCHAR(20)  NOT NULL COMMENT '断言数据',
    `is_delete`                 TINYINT(1)  DEFAULT 0 COMMENT '状态，0-未删除 1-已删除',
    `user_id`                   BIGINT  NOT NULL COMMENT '用户id',
    `create_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT ='接口信息管理表';

  # t_jenkins Jenkins信息管理表
  DROP TABLE IF EXISTS `t_jenkins`;
  CREATE TABLE `t_jenkins`
  (
      `id`                      BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
      `jenkins_name`            VARCHAR(100)        NULL COMMENT '名称',
      `jenkins_url`             VARCHAR(100)        NULL COMMENT 'Jenkins的baseUrl',
      `jenkins_username`        VARCHAR(100)        NULL COMMENT 'Jenkins认证登录用户名',
      `jenkins_password`        VARCHAR(100)        NULL COMMENT 'Jenkins认证登录密码',
      `remark`                  VARCHAR(100)        NULL COMMENT '备注',
      `is_delete`               TINYINT(1)  DEFAULT 0 COMMENT '状态，0-未删除 1-已删除',
      `user_id`                 BIGINT                 NOT NULL COMMENT '创建人id，test_user主键id',
      `create_time`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    ROW_FORMAT = DYNAMIC COMMENT ='Jenkins信息管理表';

  # t_jenkins_task Jenkins任务管理表
  DROP TABLE IF EXISTS `t_jenkins_task`;
  CREATE TABLE `t_jenkins_task`
  (
      `id`                      BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
      `jenkins_task_name`       VARCHAR(100)        NULL COMMENT 'Jenkins任务名称',
      `jenkins_id`              VARCHAR(100)        NULL COMMENT 'Jenkins信息id（主键）',
      `jenkins_job_name`        VARCHAR(100)        NULL COMMENT 'Jenkins的构建Job',
      `build_url`               VARCHAR(100)        NULL COMMENT 'Jenkins的构建url',
      `command`                 VARCHAR(100)        NULL COMMENT '执行测试命令',
      `status`                  TINYINT   DEFAULT 1 NOT NULL COMMENT '状态 0 无效 1 新建 2 执行中 3 执行完成',
      `remark`                  VARCHAR(100)        NULL COMMENT '备注',
      `user_id`                 BIGINT                 NOT NULL COMMENT '创建人id，test_user主键id',
      `create_time`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    ROW_FORMAT = DYNAMIC COMMENT ='Jenkins任务管理表';