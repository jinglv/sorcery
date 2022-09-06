package com.sorcery.platform.enums;

import lombok.Getter;

/**
 * @author jinglv
 * @date 2022/9/5 15:50
 */
@Getter
public enum ApiCodeEnum {
    /**
     * 接口返回正确
     */
    SUCCESS("00000", "成功"),

    /**
     * 接口返回错误
     */
    FAIL("00001", "失败"),

    /**
     * 服务器错误
     */
    SERVICE_ERROR("500", "服务错误"),

    /**
     * 接口返回参数不正确
     */
    PARAM_ERROR("1000", "参数不正确");


    /**
     * 业务状态码
     */
    private final String code;

    /**
     * 业务状态信息
     */
    private final String message;

    /**
     * 常量枚举，不能外部实例化
     *
     * @param code    响应编码
     * @param message 响应描述
     */
    ApiCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
