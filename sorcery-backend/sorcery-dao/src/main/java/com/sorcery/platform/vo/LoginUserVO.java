package com.sorcery.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录用户实体
 *
 * @author jinglv
 * @date 2022/9/6 11:07
 */
@EqualsAndHashCode()
@ApiModel(value = "用户登录对象", description = "用户对象user")
@Data
public class LoginUserVO {
    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "用户名", name = "username", required = true, dataType = "String", notes = "唯一不可重复", example = "tester")
    private String userName;

    /**
     * 登录用户密码
     */
    @ApiModelProperty(value = "用户名", name = "password", required = true, dataType = "String", notes = "字母+数字，6-18位", example = "tester123")
    private String password;
}
