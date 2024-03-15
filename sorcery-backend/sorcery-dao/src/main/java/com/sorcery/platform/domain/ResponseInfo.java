package com.sorcery.platform.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 接口响应信息
 *
 * @author jinglv
 * @date 2024/3/15 13:58
 */
@Data
public class ResponseInfo {
    /**
     * 状态码
     */
    private String statusCode;
    /**
     * 请求响应时长(ms)
     */
    private Long responseTime;
    /**
     * 响应头
     */
    private List<String> headers;
    /**
     * 响应内容
     */
    private Map<String, Object> body;
}
