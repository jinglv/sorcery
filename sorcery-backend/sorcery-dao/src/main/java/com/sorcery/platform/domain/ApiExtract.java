package com.sorcery.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 接口断言信息
 *
 * @author jinglv
 * @date 2023/5/9 11:16
 */
@Data
public class ApiExtract {
    /**
     * 主键
     */
    private Long id;
    /**
     * 接口信息id
     */
    private Integer apiId;
    /**
     * 接口断言名称
     */
    private String apiExtractName;
    /**
     * 提取规则
     */
    private String extract;
    /**
     * 提取值
     */
    private String value;
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
