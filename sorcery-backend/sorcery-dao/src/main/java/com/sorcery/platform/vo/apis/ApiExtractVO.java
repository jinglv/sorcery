package com.sorcery.platform.vo.apis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/9 13:29
 */
@EqualsAndHashCode()
@ApiModel(value = "接口断言提取信息对象", description = "接口断言信息")
@Data
public class ApiExtractVO {
    /**
     * 接口信息id
     */
    @ApiModelProperty(value = "接口信息id", name = "apiId", required = true, dataType = "Long", notes = "唯一不可重复", example = "1")
    private Integer apiId;
    /**
     * 接口断言名称
     */
    @ApiModelProperty(value = "接口断言名称", name = "apiExtractName", required = true, dataType = "String", notes = "接口断言名称", example = "用户名")
    private String apiExtractName;
    /**
     * 提取规则
     */
    @ApiModelProperty(value = "提取规则", name = "extract", required = true, dataType = "String", notes = "提取规则", example = "$.response.username")
    private String extract;
    /**
     * 提取值
     */
    @ApiModelProperty(value = "提取值", name = "value", required = true, dataType = "String", notes = "提取值", example = "xiaohong")
    private String value;
}
