package com.sorcery.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2022/10/8 16:39
 */
@EqualsAndHashCode()
@ApiModel(value = "项目信息对象", description = "项目信息project")
@Data
public class ProjectVO {
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名", name = "projectName", required = true, dataType = "String", notes = "唯一不可重复", example = "tester")
    private String projectName;
    /**
     * 项目备注
     */
    @ApiModelProperty(value = "项目备注", name = "description", required = true, dataType = "String", notes = "备注信息", example = "tester")
    private String description;
}
