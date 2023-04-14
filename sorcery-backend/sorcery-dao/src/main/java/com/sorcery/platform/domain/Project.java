package com.sorcery.platform.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jinglv
 * @date 2022/9/15 10:17
 */
@Data
public class Project {
    /**
     * 主键
     */
    private Long id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目备注
     */
    private String description;
    /**
     * 项目封面图片
     */
    private String image;
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
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
