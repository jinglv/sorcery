package com.sorcery.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 接口信息
 *
 * @author jinglv
 * @date 2023/5/9 11:03
 */
@Data
public class ApiInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 模块id
     */
    private Long moduleId;
    /**
     * 接口名称
     */
    private String apiName;
    /**
     * 接口地址
     */
    private String apiPath;
    /**
     * 接口请求方法
     */
    private String method;
    /**
     * 接口请求头
     */
    private String header;
    /**
     * 接口请求参数类型 1-params 2-json
     */
    private Integer paramType;
    /**
     * 接口请求主体
     */
    private String paramsBody;
    /**
     * 接口响应主体
     */
    private String response;
    /**
     * 断言类型 1-包含 2-等于
     */
    private Integer assertType;
    /**
     * 断言数据
     */
    private String assertText;
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
