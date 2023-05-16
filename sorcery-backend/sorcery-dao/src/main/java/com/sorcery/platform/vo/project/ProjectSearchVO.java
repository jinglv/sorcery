package com.sorcery.platform.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/12 16:35
 */
@EqualsAndHashCode()
@ApiModel(value = "项目信息搜索对象", description = "项目信息project")
@Data
public class ProjectSearchVO {
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名", name = "projectName", required = true, dataType = "String", notes = "唯一不可重复", example = "tester")
    private String projectName;
}
