package com.sorcery.platform.vo.apis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/16 15:37
 */
@EqualsAndHashCode()
@ApiModel(value = "接口查询信息对象", description = "接口信息api")
@Data
public class ApiInfoSearchVO {
    /**
     * 接口名称
     */
    @ApiModelProperty(value = "接口名称", name = "apiName", required = true, dataType = "String", notes = "接口名称", example = "登录接口信息")
    private String apiName;
    /**
     * 接口地址
     */
    @ApiModelProperty(value = "接口地址", name = "apiPath", required = true, dataType = "String", notes = "接口地址", example = "/api/v1/apis")
    private String apiPath;
    /**
     * 接口请求方法
     */
    @ApiModelProperty(value = "接口请求方法", name = "method", required = true, dataType = "String", notes = "接口请求方法", example = "Get")
    private String method;
}
