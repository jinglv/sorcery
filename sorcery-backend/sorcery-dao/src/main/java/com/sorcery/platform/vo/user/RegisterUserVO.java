package com.sorcery.platform.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户注册信息实体
 *
 * @author jinglv
 * @date 2022/9/6 11:24
 */
@EqualsAndHashCode()
@ApiModel(value = "用户注册信息对象", description = "用户对象user")
@Data
public class RegisterUserVO {
    /**
     * 接口传入的用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;
    /**
     * 接口传入的密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    /**
     * 接口传入的邮箱
     */
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
}
