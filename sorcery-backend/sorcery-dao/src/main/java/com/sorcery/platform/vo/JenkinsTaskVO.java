package com.sorcery.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/4/28 15:01
 */
@EqualsAndHashCode()
@ApiModel(value = "Jenkins任务信息对象", description = "Jenkins任务相关信息")
@Data
public class JenkinsTaskVO {
    /**
     * Jenkins任务名称
     */
    @ApiModelProperty(value = "Jenkins任务名称", name = "jenkinsTaskName", required = true, dataType = "String", notes = "唯一不可重复", example = "tester")
    private String jenkinsTaskName;
    /**
     * Jenkins Id
     */
    @ApiModelProperty(value = "Jenkins信息Id", name = "jenkinsId", required = true, dataType = "Integer", notes = "Jenkins Id", example = "tester")
    private Long jenkinsId;
    /**
     * Jenkins执行Job
     */
    @ApiModelProperty(value = "Jenkins Job Name", name = "jenkinsJobName", required = true, dataType = "String", notes = "Jenkins Job Name", example = "tester")
    private String jenkinsJobName;
    /**
     * Jenkins构建URL
     */
    @ApiModelProperty(value = "Jenkins构建地址", name = "buildUrl", required = true, dataType = "String", notes = "Jenkins构建url", example = "tester")
    private String buildUrl;
    /**
     * Jenkins执行命令
     */
    @ApiModelProperty(value = "Jenkins执行命令", name = "command", required = true, dataType = "Json", notes = "command", example = "tester")
    private String command;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark", required = true, dataType = "String", notes = "备注", example = "tester")
    private String remark;
}
