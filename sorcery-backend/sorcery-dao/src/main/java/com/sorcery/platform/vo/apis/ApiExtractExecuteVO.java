package com.sorcery.platform.vo.apis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author jinglv
 * @date 2023/5/10 10:05
 */
@EqualsAndHashCode()
@ApiModel(value = "接口断言执行信息对象", description = "接口断言执行信息")
@Data
public class ApiExtractExecuteVO {
    /**
     * 接口响应结果
     */
    @ApiModelProperty(value = "接口响应结果", name = "responseBody", required = true, dataType = "String", notes = "接口响应结果", example = "{\"code\": \"000000\" }")
    private String responseBody;
    /**
     * 提取规则
     */
    @ApiModelProperty(value = "提取规则", name = "extract", required = true, dataType = "List", notes = "提取规则", example = "[$.response.username]")
    private List<String> extract;
}
