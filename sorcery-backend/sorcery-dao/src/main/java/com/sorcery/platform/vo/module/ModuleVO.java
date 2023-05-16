package com.sorcery.platform.vo.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/15 17:58
 */
@EqualsAndHashCode()
@ApiModel(value = "模块信息对象", description = "模块信息module")
@Data
public class ModuleVO {
    /**
     * 模块名
     */
    @ApiModelProperty(value = "模块名", name = "moduleName", required = true, dataType = "String", notes = "唯一不可重复", example = "项目模块")
    private String moduleName;
    /**
     * 模块上级id
     */
    @ApiModelProperty(value = "模块上级id", name = "moduleParentId", required = true, dataType = "Long", notes = "模块上级id", example = "1")
    private Long moduleParentId;
    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id", name = "projectId", required = true, dataType = "Long", notes = "项目id", example = "1")
    private Long projectId;
}
