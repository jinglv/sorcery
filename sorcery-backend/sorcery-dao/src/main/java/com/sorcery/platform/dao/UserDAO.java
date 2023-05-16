package com.sorcery.platform.dao;

import com.sorcery.platform.domain.User;
import com.sorcery.platform.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author jinglv
 * @date 2022/9/1 10:32
 */
@Mapper
public interface UserDAO {
    /**
     * 根据用户手机号码查询用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    User getUserByUserName(String userName);

    /**
     * 用户注册
     *
     * @param user 传入用户信息
     * @return int
     */
    Integer addUser(User user);

    /**
     * 新增用户信息
     *
     * @param userInfo 传入用户基础信息
     * @return int
     */
    Integer addUserInfo(UserInfo userInfo);

    /**
     * 查询用户
     *
     * @param userId 用户id
     * @return 用户
     */
    User getUserById(Long userId);

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @return int
     */
    Integer updateUserInfo(UserInfo userInfo);

    /**
     * 根据userId查询RefreshToken
     *
     * @param userId 用户id
     * @return 查询结果
     */
    String getRefreshTokenByUserId(Long userId);

    /**
     * 根据用户id删除refresh toke
     *
     * @param userId 用户id
     * @return int
     */
    Integer deleteRefreshTokenByUserId(Long userId);

    /**
     * 新增refresh token
     *
     * @param refreshToken refresh token
     * @param userId       用户id
     * @param createTime   创建时间
     * @param updateTime   更新时间
     * @return int
     */
    Integer addRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId, @Param("createTime") Date createTime, @Param("updateTime") Date updateTime);

    /**
     * 用户注销
     *
     * @param refreshToken refresh token
     * @param userId       用户id
     * @return int
     */
    Integer deleteRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId);
}
