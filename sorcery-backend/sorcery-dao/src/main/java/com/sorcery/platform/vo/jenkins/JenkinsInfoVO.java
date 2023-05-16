package com.sorcery.platform.vo.jenkins;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinglv
 * @date 2023/4/27 10:43
 */
@EqualsAndHashCode()
@ApiModel(value = "Jenkins信息对象", description = "Jenkins相关信息")
@Data
public class JenkinsInfoVO {
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
    /**
     * Jenkins认证登录用户名
     */
    @ApiModelProperty(value = "Jenkins认证登录用户名", name = "jenkinsUsername", required = true, dataType = "String", notes = "Jenkins登录用户名", example = "tester")
    private String jenkinsUsername;
    /**
     * Jenkins认证登录密码
     */
    @ApiModelProperty(value = "Jenkins认证登录密码", name = "jenkinsPassword", required = true, dataType = "String", notes = "Jenkins登录用户密码", example = "tester")
    private String jenkinsPassword;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark", required = true, dataType = "String", notes = "备注", example = "tester")
    private String remark;
}
