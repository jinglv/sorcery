package com.sorcery.platform.domain;

import com.sorcery.platform.enums.ApiCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jinglv
 * @date 2022/8/31 16:25
 */
@Data
@ApiModel(value = "JsonResponse", description = "响应参数说明")
public class JsonResponse<T> {

    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应编码，成功返回：00000", example = "00000", required = true)
    private String code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "响应描述", example = "ok", required = true)
    private String msg;

    /**
     * 响应结果数据
     */
    @ApiModelProperty(value = "响应结果数据", required = true)
    private T data;

    /**
     * 接口日志跟踪Id
     */
    @ApiModelProperty(value = "TraceId跟踪日志id", required = true)
    private String traceId;

    private JsonResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResponse<T> success() {
        return ofCode(ApiCodeEnum.SUCCESS, null);
    }

    public static <T> JsonResponse<T> success(T data) {
        return ofCode(ApiCodeEnum.SUCCESS, data);
    }

    public static <T> JsonResponse<T> fail(T data) {
        return ofCode(ApiCodeEnum.FAIL, data);
    }

    public static <T> JsonResponse<T> fail() {
        return ofCode(ApiCodeEnum.FAIL, null);
    }

    public static <T> JsonResponse<T> ofCode(ApiCodeEnum apiCodeEnum, T data) {
        return new JsonResponse<>(apiCodeEnum.getCode(), apiCodeEnum.getMessage(), data);
    }
}
