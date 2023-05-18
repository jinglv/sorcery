package com.sorcery.platform.vo.jenkins;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/5/17 15:09
 */
@EqualsAndHashCode()
@ApiModel(value = "Jenkins信息搜索对象", description = "Jenkins相关信息")
@Data
public class JenkinsInfoSearchVO {
    /**
     * Jenkins名称
     */
    @ApiModelProperty(value = "Jenkins名称", name = "jenkinsName", required = true, dataType = "String", notes = "唯一不可重复", example = "tester")
    private String jenkinsName;
    /**
     * Jenkins基础url
     */
    @ApiModelProperty(value = "Jenkins基础url", name = "jenkinsUrl", required = true, dataType = "String", notes = "Jenkins Base Url", example = "tester")
    private String jenkinsUrl;
}
