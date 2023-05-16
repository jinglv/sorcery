package com.sorcery.platform.bo;

import lombok.Data;

/**
 * @author jinglv
 * @date 2023/5/15 17:17
 */
@Data
public class ModuleAndProjectBO {
    /**
     * 项目Id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 模块Id
     */
    private Long moduleId;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 模块上级id
     */
    private Integer moduleParentId;
}
