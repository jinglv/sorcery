package com.sorcery.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Jenkins信息
 *
 * @author jinglv
 * @date 2023/4/27 10:32
 */
@Data
public class JenkinsInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * Jenkins名称
     */
    private String jenkinsName;
    /**
     * Jenkins基础url
     */
    private String jenkinsUrl;
    /**
     * Jenkins认证登录用户名
     */
    private String jenkinsUsername;
    /**
     * Jenkins认证登录密码
     */
    private String jenkinsPassword;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 用户ID
     */
    private Long userId;
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
