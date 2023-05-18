package com.sorcery.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 模块信息
 *
 * @author jinglv
 * @date 2023/5/9 11:00
 */
@Data
public class Modules {
    /**
     * 主键
     */
    private Long id;
    /**
     * 模块名称
     */
    private String label;
    /**
     * 模块上级id
     */
    private Long moduleParentId;
    /**
     * 项目id
     */
    private Long projectId;
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
    /**
     * 子模块信息
     */
    private List<Modules> children;
}
