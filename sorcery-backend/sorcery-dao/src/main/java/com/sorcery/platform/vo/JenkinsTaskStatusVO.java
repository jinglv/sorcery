package com.sorcery.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/8 11:00
 */
@EqualsAndHashCode()
@ApiModel(value = "Jenkins任务状态变更信息对象", description = "Jenkins任务相关信息")
@Data
public class JenkinsTaskStatusVO {
    /**
     * Jenkins名称
     */
    @ApiModelProperty(value = "Jenkins任务Id", name = "Jenkins Task Id", required = true, dataType = "Long", notes = "唯一不可重复", example = "tester")
    private Long jenkinsTaskId;
    /**
     * Jenkins Job构建url
     */
    @ApiModelProperty(value = "buildUrl", name = "buildUrl", required = true, dataType = "String", notes = "Build Url", example = "tester")
    private String buildUrl;
    /**
     * 任务状态码
     */
    @ApiModelProperty(value = "任务状态码", name = "status", required = true, dataType = "String", notes = "任务状态码", example = "tester")
    private Integer status;
}
