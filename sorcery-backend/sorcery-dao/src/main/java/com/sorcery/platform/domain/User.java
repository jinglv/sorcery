package com.sorcery.platform.domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户表实体
 *
 * @author jinglv
 * @date 2022/9/1 10:21
 */
@Data
public class User {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 密随机盐
     */
    private String salt;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 用户信息
     */
    private UserInfo userInfo;
}
