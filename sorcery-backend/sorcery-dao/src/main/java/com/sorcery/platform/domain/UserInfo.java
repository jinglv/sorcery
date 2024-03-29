package com.sorcery.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息实体
 *
 * @author jinglv
 * @date 2022/9/1 10:27
 */
@Data
public class UserInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 签名
     */
    private String sign;
    /**
     * 性别
     */
    private String gender;
    /**
     * 生日
     */
    private String birth;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
