package com.sorcery.platform.vo.apis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/9 11:36
 */
@EqualsAndHashCode()
@ApiModel(value = "接口信息对象", description = "接口信息api")
@Data
public class ApiInfoVO {
    /**
     * 模块id
     */
    @ApiModelProperty(value = "模块Id", name = "moduleId", required = true, dataType = "Long", notes = "唯一不可重复", example = "1")
    private Long moduleId;
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
    /**
     * 接口响应主体
     */
    @ApiModelProperty(value = "接口响应主体", name = "response", required = true, dataType = "Json", notes = "接口响应主体", example = "{\"code\": \"00000\" }")
    private String response;
    /**
     * 断言类型 1-包含 2-等于
     */
    @ApiModelProperty(value = "断言类型", name = "assertType", required = true, dataType = "Integer", notes = "断言类型 1-包含 2-等于", example = "1")
    private Integer assertType;
    /**
     * 断言数据
     */
    @ApiModelProperty(value = "断言数据", name = "assertText", required = true, dataType = "String", notes = "断言数据", example = "username")
    private String assertText;
}
