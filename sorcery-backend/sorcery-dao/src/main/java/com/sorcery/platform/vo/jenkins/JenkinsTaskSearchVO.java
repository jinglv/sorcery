package com.sorcery.platform.vo.jenkins;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/18 10:38
 */
@EqualsAndHashCode()
@ApiModel(value = "Jenkins任务搜索对象", description = "Jenkins任务相关信息")
@Data
public class JenkinsTaskSearchVO {
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
}
