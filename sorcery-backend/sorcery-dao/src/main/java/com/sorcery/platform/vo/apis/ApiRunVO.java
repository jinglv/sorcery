package com.sorcery.platform.vo.apis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/9 16:46
 */
@EqualsAndHashCode()
@ApiModel(value = "执行接口信息对象", description = "接口信息api")
@Data
public class ApiRunVO {
    /**
     * url
     */
    @ApiModelProperty(value = "url", name = "url", required = true, dataType = "String", notes = "url", example = "http://127.0.0.1:8080")
    private String url;
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
    /**
     * 接口请求头
     */
    @ApiModelProperty(value = "接口请求头", name = "header", required = true, dataType = "Json", notes = "接口请求头", example = "{\"Content-type\":\"application/json\"}")
    private String header;
    /**
     * 接口请求参数类型 1-params 2-json
     */
    @ApiModelProperty(value = "接口请求参数类型", name = "paramType", required = true, dataType = "Integer", notes = "接口请求参数类型 1-params 2-json", example = "1")
    private Integer paramType;
    /**
     * 接口请求主体
     */
    @ApiModelProperty(value = "接口请求主体", name = "paramsBody", required = true, dataType = "Json", notes = "接口请求主体", example = "{\"username\": \"admin\" }")
    private String paramsBody;
}
