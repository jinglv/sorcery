package com.sorcery.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jinglv
 * @date 2023/4/27 16:39
 */
@Data
public class JenkinsTask {
    /**
     * 主键
     */
    private Long id;
    /**
     * Jenkins任务名称
     */
    private String jenkinsTaskName;
    /**
     * Jenkins Id
     */
    private Long jenkinsId;
    /**
     * Jenkins执行Job
     */
    private String jenkinsJobName;
    /**
     * Jenkins构建URL
     */
    private String buildUrl;
    /**
     * Jenkins执行命令
     */
    private String command;
    /**
     * 状态 0 无效 1 新建 2 执行中 3 执行完成
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
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
