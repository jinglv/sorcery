package com.sorcery.platform.service;

import com.sorcery.platform.domain.User;
import com.sorcery.platform.domain.UserInfo;
import com.sorcery.platform.vo.user.LoginUserVO;
import com.sorcery.platform.vo.user.RegisterUserVO;

import java.util.Map;

/**
 * @author jinglv
 * @date 2023/1/10 15:28
 */
public interface UserService {
    /**
     * 用户注册（保存用户信息）
     *
     * @param registerUser 用户注册实体
     */
    void addUser(RegisterUserVO registerUser);

    /**
     * 用户登录
     *
     * @param user 用户登录实体
     * @return map
     */
    Map<String, String> login(LoginUserVO user) throws Exception;

    /**
     * 用户登录
     *
     * @param user 用户登录实体
     * @return map
     */
    Map<String, Object> loginForDts(LoginUserVO user) throws Exception;

    /**
     * 登出
     *
     * @param refreshToken token
     * @param userId       用户id
     */
    void logout(String refreshToken, Long userId);

    /**
     * 通过用户登录名查询用户信息
     *
     * @param userName 用户登录名
     * @return 用户信息
     */
    User getUserByUserName(String userName);

    /**
     * 通过用户ID查询用户信息
     *
     * @param userId 用户Id
     * @return 用户信息
     */
    User getUserInfo(Long userId);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     */
    void updateUserInfo(UserInfo userInfo);

}
